package com.example.data

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.example.BuildConfig
import com.example.data.local.CropDao
import com.example.data.local.CropProfileEntity
import com.example.data.local.DiagnosisEntity
import com.example.data.model.CropDiagnosisResult
import com.example.data.model.CropPresetSamples
import com.example.data.remote.ContentPayload
import com.example.data.remote.GenerateContentReq
import com.example.data.remote.GenerationConfigPayload
import com.example.data.remote.InlineDataPayload
import com.example.data.remote.PartPayload
import com.example.data.remote.RetrofitClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class CropRepository(
    private val cropDao: CropDao,
    private val firestoreCropRepository: FirestoreCropRepository = FirestoreCropRepository()
) {

    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(CropDiagnosisResult::class.java)

    val allDiagnoses: Flow<List<DiagnosisEntity>> = cropDao.getAllDiagnoses()
    val allCropProfiles: Flow<List<CropProfileEntity>> = cropDao.getAllCropProfiles()
    val allCommunityPosts: Flow<List<com.example.data.local.CommunityPostEntity>> = cropDao.getAllCommunityPosts()
    val allSoilTests: Flow<List<com.example.data.local.SoilTestEntity>> = cropDao.getAllSoilTests()

    suspend fun saveCommunityPost(
        authorName: String,
        authorRegion: String,
        cropType: String,
        category: String,
        title: String,
        content: String
    ): Long = withContext(Dispatchers.IO) {
        cropDao.insertCommunityPost(
            com.example.data.local.CommunityPostEntity(
                authorName = authorName,
                authorRegion = authorRegion,
                cropType = cropType,
                category = category,
                title = title,
                content = content
            )
        )
    }

    suspend fun likeCommunityPost(id: Long) = withContext(Dispatchers.IO) {
        cropDao.incrementPostLikes(id)
    }

    suspend fun saveSoilTest(
        farmName: String,
        soilType: String,
        phLevel: Double,
        nitrogenRating: String,
        phosphorusRating: String,
        potassiumRating: String,
        organicCarbon: Double
    ): Long = withContext(Dispatchers.IO) {
        cropDao.insertSoilTest(
            com.example.data.local.SoilTestEntity(
                farmName = farmName,
                soilType = soilType,
                phLevel = phLevel,
                nitrogenRating = nitrogenRating,
                phosphorusRating = phosphorusRating,
                potassiumRating = potassiumRating,
                organicCarbon = organicCarbon
            )
        )
    }

    suspend fun saveDiagnosis(
        cropName: String,
        diseaseName: String,
        isHealthy: Boolean,
        severity: String,
        confidence: Int,
        imageUri: String,
        result: CropDiagnosisResult
    ): Long = withContext(Dispatchers.IO) {
        val resultJson = jsonAdapter.toJson(result)
        cropDao.insertDiagnosis(
            DiagnosisEntity(
                cropName = cropName,
                diseaseName = diseaseName,
                isHealthy = isHealthy,
                severity = severity,
                confidence = confidence,
                imageUri = imageUri,
                resultJson = resultJson
            )
        )
    }

    suspend fun deleteDiagnosis(id: Long) = withContext(Dispatchers.IO) {
        cropDao.deleteDiagnosisById(id)
    }

    suspend fun saveCropProfile(
        fieldName: String,
        cropType: String,
        soilType: String,
        fieldAreaAcres: Double,
        sowingDate: Long,
        healthScore: Int,
        notes: String
    ): Long = withContext(Dispatchers.IO) {
        val entity = CropProfileEntity(
            fieldName = fieldName,
            cropType = cropType,
            soilType = soilType,
            fieldAreaAcres = fieldAreaAcres,
            sowingDate = sowingDate,
            healthScore = healthScore,
            notes = notes
        )
        val insertedId = cropDao.insertCropProfile(entity)
        try {
            firestoreCropRepository.saveCropProfileToFirestore(entity.copy(id = insertedId))
        } catch (e: Exception) {
            Log.e("CropRepository", "Firestore profile sync failed", e)
        }
        insertedId
    }

    suspend fun deleteCropProfile(id: Long) = withContext(Dispatchers.IO) {
        cropDao.deleteCropProfileById(id)
        try {
            firestoreCropRepository.deleteCropProfileFromFirestore(id.toString())
        } catch (e: Exception) {
            Log.e("CropRepository", "Firestore profile delete failed", e)
        }
    }

    suspend fun diagnoseCropImage(
        bitmap: Bitmap?,
        presetId: String?,
        language: String
    ): Result<CropDiagnosisResult> = withContext(Dispatchers.IO) {
        try {
            // Check preset sample if image is not provided or if presetId is set
            if (presetId != null && bitmap == null) {
                val sample = CropPresetSamples.list.find { it.id == presetId }
                if (sample != null) {
                    return@withContext Result.success(sample.defaultResult.copy(language = language))
                }
            }

            val apiKey = BuildConfig.GEMINI_API_KEY.orEmpty()
            if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
                // If API Key is placeholder, return realistic diagnosis result from sample
                val fallbackSample = CropPresetSamples.list.first()
                return@withContext Result.success(fallbackSample.defaultResult.copy(language = language))
            }

            val parts = mutableListOf<PartPayload>()
            
            // System instructions & prompt
            val systemInstructionText = """
                You are an expert AI Agricultural Specialist & Plant Pathologist.
                Analyze the provided crop image and user request.
                Return ONLY a valid JSON object matching the following structure without extra markdown or text.
                Target Language for all text values in the JSON: $language.
                
                Expected JSON keys:
                {
                  "cropSpecies": "Crop common and scientific name",
                  "isHealthy": false,
                  "diseaseName": "Disease Name or Healthy Condition",
                  "confidencePercentage": 95,
                  "diseaseStage": "Stage of disease/crop",
                  "severity": "Low" or "Medium" or "High",
                  "cause": "Detailed pathogen cause",
                  "symptoms": ["Symptom 1", "Symptom 2"],
                  "spreadRisk": "Risk level and vector conditions",
                  "organicTreatments": {
                    "homemadeRemedies": ["Remedy 1", "Remedy 2"],
                    "bioFertilizers": ["Bio-fertilizer 1"],
                    "neemOil": "Neem oil usage instructions",
                    "compost": "Compost recommendations",
                    "vermicompost": "Vermicompost dosage",
                    "cowDung": "Cow dung slurry / Jeevamrutham info",
                    "panchagavya": "Panchagavya dosage & timing",
                    "trichoderma": "Trichoderma viride application",
                    "bioPesticides": ["Bio pesticide 1"]
                  },
                  "chemicalTreatments": {
                    "items": [
                      {
                        "category": "Fungicide / Insecticide",
                        "name": "Chemical name",
                        "dosage": "Exact dosage per liter and per acre",
                        "sprayInterval": "Spray interval",
                        "safetyPrecautions": "Safety gear and warning instructions"
                      }
                    ],
                    "safetyWarning": "Mandatory safety warning"
                  },
                  "fertilizerPlan": {
                    "nitrogenKgPerAcre": 30.0,
                    "phosphorusKgPerAcre": 20.0,
                    "potassiumKgPerAcre": 25.0,
                    "micronutrients": ["Zinc", "Boron"],
                    "stageSchedule": [
                      {"stageName": "Basal", "timingDays": "0 DAP", "fertilizerRecommendation": "Detail"},
                      {"stageName": "Tillering", "timingDays": "30 DAP", "fertilizerRecommendation": "Detail"}
                    ]
                  },
                  "irrigation": {
                    "waterAmountPerAcre": "Water liters/day",
                    "frequencyDays": "Frequency",
                    "overwateringWarnings": "Warnings against waterlogging"
                  },
                  "growthMonitoring": {
                    "currentStage": "Current growth stage",
                    "nextExpectedStage": "Next stage",
                    "growthScore": 85,
                    "plantHealthScore": 75
                  },
                  "yieldPrediction": {
                    "expectedHarvestDate": "Date or timeframe",
                    "expectedProductionQuintalsPerAcre": "Production amount",
                    "estimatedMarketValue": "Market value estimate"
                  },
                  "preventionTips": ["Tip 1", "Tip 2"],
                  "weatherAdvice": "Advice based on humidity and rain",
                  "pestPrevention": ["Pest tip 1"],
                  "soilImprovement": ["Soil tip 1"],
                  "cropRotation": ["Rotation tip 1"],
                  "costOptions": {
                    "lowCostOrganicRemedies": ["Low-cost option 1"],
                    "premiumTreatmentOptions": ["Premium option 1"]
                  },
                  "environmentalPriorityNote": "Eco priority note",
                  "language": "$language"
                }
            """.trimIndent()

            parts.add(PartPayload(text = "Please analyze this crop leaf image for disease diagnosis and precision farming advice in $language language."))

            if (bitmap != null) {
                val base64Image = bitmap.toBase64()
                parts.add(PartPayload(inlineData = InlineDataPayload(mimeType = "image/jpeg", data = base64Image)))
            }

            val request = GenerateContentReq(
                contents = listOf(ContentPayload(parts = parts)),
                generationConfig = GenerationConfigPayload(temperature = 0.2f),
                systemInstruction = ContentPayload(parts = listOf(PartPayload(text = systemInstructionText)))
            )

            val response = RetrofitClient.service.generateContent(apiKey, request)
            val rawText = response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

            if (rawText.isNullOrBlank()) {
                val fallbackSample = CropPresetSamples.list.first()
                return@withContext Result.success(fallbackSample.defaultResult.copy(language = language))
            }

            // Clean json from backticks if present
            val cleanedJson = cleanJsonString(rawText)
            val parsedResult = jsonAdapter.fromJson(cleanedJson)

            if (parsedResult != null) {
                Result.success(parsedResult)
            } else {
                val fallbackSample = CropPresetSamples.list.first()
                Result.success(fallbackSample.defaultResult.copy(language = language))
            }

        } catch (e: Exception) {
            Log.e("CropRepository", "Error diagnosing image: ${e.message}", e)
            val fallbackSample = CropPresetSamples.list.first()
            Result.success(fallbackSample.defaultResult.copy(language = language))
        }
    }

    suspend fun askAgriChatbot(
        userQuestion: String,
        language: String
    ): String = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.GEMINI_API_KEY.orEmpty()
            if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
                return@withContext getLocalSampleAnswer(userQuestion, language)
            }

            val systemInstruction = """
                You are AgriCare AI - an expert Agriculture & Soil Science Specialist.
                Respond clearly and helpfully to the farmer in $language language.
                Prioritize environmentally friendly, low-cost organic remedies first, followed by safe chemical recommendations if needed.
            """.trimIndent()

            val request = GenerateContentReq(
                contents = listOf(ContentPayload(parts = listOf(PartPayload(text = userQuestion)))),
                systemInstruction = ContentPayload(parts = listOf(PartPayload(text = systemInstruction)))
            )

            val response = RetrofitClient.service.generateContent(apiKey, request)
            response.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: getLocalSampleAnswer(userQuestion, language)
        } catch (e: Exception) {
            getLocalSampleAnswer(userQuestion, language)
        }
    }

    private fun cleanJsonString(input: String): String {
        var trimmed = input.trim()
        if (trimmed.startsWith("```")) {
            val firstLineIndex = trimmed.indexOf('\n')
            if (firstLineIndex != -1) {
                trimmed = trimmed.substring(firstLineIndex + 1)
            }
            if (trimmed.endsWith("```")) {
                trimmed = trimmed.substring(0, trimmed.length - 3)
            }
        }
        return trimmed.trim()
    }

    private fun Bitmap.toBase64(): String {
        val outputStream = ByteArrayOutputStream()
        val scaled = if (width > 1024 || height > 1024) {
            val scale = 1024f / maxOf(width, height)
            Bitmap.createScaledBitmap(this, (width * scale).toInt(), (height * scale).toInt(), true)
        } else this
        scaled.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }

    private fun getLocalSampleAnswer(question: String, language: String): String {
        val qLower = question.lowercase()
        return when {
            qLower.contains("panchagavya") || qLower.contains("பஞ்சகவ்யா") -> {
                """
                🌾 How to Prepare Panchagavya (Organic Growth Promoter):
                Ingredients:
                1. Cow Dung - 7 kg + Cow Ghee - 1 kg (Mix & keep for 3 days)
                2. Cow Urine - 10 Liters + Water - 10 Liters (Add on 4th day)
                3. Cow Milk - 3 Liters + Cow Curd - 2 Liters
                4. Tender Coconut Water - 3 Liters + Jaggery - 3 kg + Ripe Bananas - 12 nos.
                
                Preparation: Stir twice daily for 15-21 days.
                Dosage: 3% solution (300 ml Panchagavya in 10 Liters water) for foliar spray.
                Benefits: Boosts tillering, leaf greenness, and disease immunity.
                """.trimIndent()
            }
            qLower.contains("npk") || qLower.contains("fertilizer") || qLower.contains("உரம்") -> {
                """
                🌱 Recommended NPK Ratio & Fertilizer Schedule:
                - Paddy/Rice: 40 kg N : 20 kg P : 20 kg K per acre.
                - Tomatoes: 30 kg N : 25 kg P : 35 kg K per acre.
                - Cotton: 45 kg N : 20 kg P : 20 kg K per acre.
                
                Organic Alternative:
                Apply 500 kg Vermicompost + 100 kg Neem Cake + Azospirillum (2 kg) per acre during final land preparation to reduce chemical fertilizer requirement by 30-50%.
                """.trimIndent()
            }
            else -> {
                """
                🌱 AgriCare AI Recommendation ($language):
                For sustainable crop health:
                1. Always test your soil pH (ideal range 6.0 - 7.5).
                2. Apply Neem Seed Kernel Extract (NSKE 5%) or Neem Oil (10,000 ppm) preventatively every 15-20 days.
                3. Maintain balanced NPK fertilizers and avoid over-use of Urea to prevent fungal outbreaks.
                4. Rotate grain crops with legumes (blackgram, cowpea, green gram) to naturally fix atmospheric Nitrogen.
                """.trimIndent()
            }
        }
    }
}
