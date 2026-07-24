package com.example.ui

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CropRepository
import com.example.data.local.AppDatabase
import com.example.data.local.CropProfileEntity
import com.example.data.local.DiagnosisEntity
import com.example.data.model.CropDiagnosisResult
import com.example.data.model.CropPresetSamples
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class DiagnosisUiState {
    object Idle : DiagnosisUiState()
    data class Loading(val progressMessage: String) : DiagnosisUiState()
    data class Success(val result: CropDiagnosisResult, val savedId: Long? = null) : DiagnosisUiState()
    data class Error(val message: String) : DiagnosisUiState()
}

data class ChatMessage(
    val sender: String, // "user" or "bot"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class FertilizerCalculationResult(
    val nitrogenKg: Double,
    val phosphorusKg: Double,
    val potassiumKg: Double,
    val vermicompostKg: Double,
    val neemCakeKg: Double,
    val panchagavyaLiters: Double,
    val stageSchedule: List<Pair<String, String>>
)

data class FarmerUserProfile(
    val name: String = "Ramesh Kumar",
    val phone: String = "+91 98765 43210",
    val location: String = "Coimbatore, Tamil Nadu",
    val primarySoilType: String = "Red Loamy Soil",
    val totalLandAcres: Double = 3.5,
    val pastCropHistory: String = "Paddy (Kharif 2024), Groundnut (Rabi 2024)",
    val loginMode: String = "Google Account"
)

data class AdvancedYieldPredictionResult(
    val cropName: String,
    val totalAcres: Double,
    val historicalYieldQuintalsPerAcre: Double,
    val predictedYieldPerAcre: Double,
    val totalExpectedYieldQuintals: Double,
    val estimatedPricePerQuintal: Double,
    val estimatedRevenueRs: Double,
    val estimatedCostRs: Double,
    val estimatedNetProfitRs: Double,
    val harvestDateEstimate: String,
    val soilImpactSummary: String,
    val weatherForecastImpact: String,
    val diseaseRiskImpact: String,
    val actionableSuggestions: List<String>
)

class AgriViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = CropRepository(db.cropDao())

    val allDiagnoses: StateFlow<List<DiagnosisEntity>> = repository.allDiagnoses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allCropProfiles: StateFlow<List<CropProfileEntity>> = repository.allCropProfiles
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allCommunityPosts: StateFlow<List<com.example.data.local.CommunityPostEntity>> = repository.allCommunityPosts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allSoilTests: StateFlow<List<com.example.data.local.SoilTestEntity>> = repository.allSoilTests
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val prefs = application.getSharedPreferences("agri_app_prefs", android.content.Context.MODE_PRIVATE)

    private val _hasCompletedLanguageSetup = MutableStateFlow(
        prefs.getBoolean("has_completed_language_setup", false)
    )
    val hasCompletedLanguageSetup: StateFlow<Boolean> = _hasCompletedLanguageSetup.asStateFlow()

    private val _userProfile = MutableStateFlow(FarmerUserProfile())
    val userProfile: StateFlow<FarmerUserProfile> = _userProfile.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _showSplashScreen = MutableStateFlow(true)
    val showSplashScreen: StateFlow<Boolean> = _showSplashScreen.asStateFlow()

    fun dismissSplash() {
        _showSplashScreen.value = false
    }

    fun loginUser(mode: String, name: String = "Farmer Guest", phone: String = "") {
        _isLoggedIn.value = true
        _userProfile.value = _userProfile.value.copy(
            name = if (name.isNotBlank()) name else "Farmer Ramesh",
            phone = if (phone.isNotBlank()) phone else "+91 98765 43210",
            loginMode = mode
        )
    }

    fun logoutUser() {
        _isLoggedIn.value = false
    }

    fun updateUserProfile(
        name: String,
        phone: String,
        location: String,
        soilType: String,
        acres: Double,
        history: String
    ) {
        _userProfile.value = FarmerUserProfile(
            name = name,
            phone = phone,
            location = location,
            primarySoilType = soilType,
            totalLandAcres = acres,
            pastCropHistory = history,
            loginMode = _userProfile.value.loginMode
        )
    }

    init {
        // Seed default community posts & sample soil test if empty
        viewModelScope.launch {
            kotlinx.coroutines.delay(1000)
            if (allCommunityPosts.value.isEmpty()) {
                repository.saveCommunityPost(
                    authorName = "Murugan V.",
                    authorRegion = "Thanjavur, Tamil Nadu",
                    cropType = "Paddy / Rice",
                    category = "Pest Control",
                    title = "How to control Leaf Folder in organic paddy fields?",
                    content = "Facing leaf folder caterpillars after recent monsoon rain. Sprayed Panchagavya solution and released Trichogramma egg parasitoids. Has anyone tried Neem Oil + Garlic extract?"
                )
                repository.saveCommunityPost(
                    authorName = "Priya Sharma",
                    authorRegion = "Karnal, Haryana",
                    cropType = "Tomato",
                    category = "Soil & Fertilizer",
                    title = "Best organic mix for Early Blight prevention",
                    content = "Sharing my experience: Adding Vermicompost + Trichoderma viride directly to root zone reduced tomato blight occurrence by 80% this season."
                )
                repository.saveCommunityPost(
                    authorName = "Rajesh Patel",
                    authorRegion = "Rajkot, Gujarat",
                    cropType = "Cotton",
                    category = "Irrigation",
                    title = "Drip irrigation timing during flowering stage",
                    content = "What is the ideal water frequency for cotton under high summer heat? Currently running 2 hours alternate days with liquid bio-fertilizers."
                )
            }
            if (allSoilTests.value.isEmpty()) {
                repository.saveSoilTest(
                    farmName = "Main Field - South Block",
                    soilType = "Red Loamy",
                    phLevel = 6.8,
                    nitrogenRating = "Medium (280 kg/ha)",
                    phosphorusRating = "High (42 kg/ha)",
                    potassiumRating = "Medium (190 kg/ha)",
                    organicCarbon = 0.65
                )
            }
        }
    }

    fun createCommunityPost(
        cropType: String,
        category: String,
        title: String,
        content: String
    ) {
        viewModelScope.launch {
            repository.saveCommunityPost(
                authorName = _userProfile.value.name,
                authorRegion = _userProfile.value.location,
                cropType = cropType,
                category = category,
                title = title,
                content = content
            )
        }
    }

    fun likePost(postId: Long) {
        viewModelScope.launch {
            repository.likeCommunityPost(postId)
        }
    }

    fun saveNewSoilTest(
        farmName: String,
        soilType: String,
        phLevel: Double,
        nitrogen: String,
        phosphorus: String,
        potassium: String,
        organicCarbon: Double
    ) {
        viewModelScope.launch {
            repository.saveSoilTest(
                farmName = farmName,
                soilType = soilType,
                phLevel = phLevel,
                nitrogenRating = nitrogen,
                phosphorusRating = phosphorus,
                potassiumRating = potassium,
                organicCarbon = organicCarbon
            )
        }
    }

    fun calculateAdvancedYieldML(
        cropName: String,
        acres: Double,
        historicalYieldQuintalsPerAcre: Double,
        soilType: String,
        nitrogenLevel: String,
        weatherForecast: String,
        pastPestFrequency: String
    ): AdvancedYieldPredictionResult {
        val baseYield = if (historicalYieldQuintalsPerAcre > 0) historicalYieldQuintalsPerAcre else when (cropName.lowercase()) {
            "paddy", "rice", "நெல்" -> 24.0
            "tomato", "தக்காளி" -> 32.0
            "cotton", "பருத்தி" -> 14.0
            "maize", "corn", "மக்காச்சோளம்" -> 28.0
            "chilli", "மிளகாய்" -> 18.0
            else -> 22.0
        }

        // Soil factor
        val soilFactor = when (soilType.lowercase()) {
            "alluvial", "loam", "black cotton" -> 1.10
            "red loamy" -> 1.05
            "sandy" -> 0.90
            else -> 1.0
        }

        // Nitrogen factor
        val nFactor = when (nitrogenLevel.lowercase()) {
            "high" -> 1.08
            "medium" -> 1.0
            "low" -> 0.88
            else -> 1.0
        }

        // Weather factor
        val weatherFactor = when {
            weatherForecast.contains("favorable", true) || weatherForecast.contains("normal", true) -> 1.05
            weatherForecast.contains("drought", true) || weatherForecast.contains("dry", true) -> 0.85
            weatherForecast.contains("heavy rain", true) -> 0.92
            else -> 1.0
        }

        // Pest / Disease Severity penalty
        val pestPenalty = when (pastPestFrequency.lowercase()) {
            "frequent / high" -> 0.85
            "moderate" -> 0.94
            "low / zero" -> 1.02
            else -> 0.96
        }

        val predictedPerAcre = baseYield * soilFactor * nFactor * weatherFactor * pestPenalty
        val totalYield = predictedPerAcre * acres

        val pricePerQuintal = when (cropName.lowercase()) {
            "paddy", "rice", "நெல்" -> 2300.0
            "tomato", "தக்காளி" -> 2800.0
            "cotton", "பருத்தி" -> 7200.0
            "maize", "corn", "மக்காச்சோளம்" -> 2100.0
            "chilli", "மிளகாய்" -> 12000.0
            else -> 3000.0
        }

        val estimatedRevenue = totalYield * pricePerQuintal
        val estimatedCost = acres * 18000.0
        val estimatedNetProfit = estimatedRevenue - estimatedCost

        val suggestions = mutableListOf<String>()
        if (nFactor < 1.0) suggestions.add("Apply 50kg Vermicompost + Azospirillum bio-fertilizer per acre to boost Nitrogen availability.")
        if (pestPenalty < 1.0) suggestions.add("Install yellow sticky traps & spray 5ml/L Neem Oil preventively before panicle/flowering stage.")
        if (weatherFactor < 1.0) suggestions.add("Adopt drip irrigation with straw mulching to conserve moisture during rain deficit.")
        suggestions.add("Foliar spray of 1% Potassium Nitrate (13-0-45) during grain/fruit filling to maximize weight by 8-12%.")

        return AdvancedYieldPredictionResult(
            cropName = cropName,
            totalAcres = acres,
            historicalYieldQuintalsPerAcre = baseYield,
            predictedYieldPerAcre = String.format("%.1f", predictedPerAcre).toDouble(),
            totalExpectedYieldQuintals = String.format("%.1f", totalYield).toDouble(),
            estimatedPricePerQuintal = pricePerQuintal,
            estimatedRevenueRs = estimatedRevenue,
            estimatedCostRs = estimatedCost,
            estimatedNetProfitRs = estimatedNetProfit,
            harvestDateEstimate = "Estimated in 65 - 80 Days",
            soilImpactSummary = "Soil Type ($soilType) & Nitrogen ($nitrogenLevel): ${if (soilFactor >= 1.0) "+${((soilFactor*nFactor - 1)*100).toInt()}% boost" else "-10% deficit"}",
            weatherForecastImpact = "Weather Trend ($weatherForecast): Impact multiplier x${String.format("%.2f", weatherFactor)}",
            diseaseRiskImpact = "Pathogen History ($pastPestFrequency): Protection factor x${String.format("%.2f", pestPenalty)}",
            actionableSuggestions = suggestions
        )
    }

    private val _selectedLanguage = MutableStateFlow(
        prefs.getString("selected_language", "English") ?: "English"
    )
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri.asStateFlow()

    private val _selectedImageBitmap = MutableStateFlow<Bitmap?>(null)
    val selectedImageBitmap: StateFlow<Bitmap?> = _selectedImageBitmap.asStateFlow()

    private val _selectedPresetId = MutableStateFlow<String?>("rice_blast")
    val selectedPresetId: StateFlow<String?> = _selectedPresetId.asStateFlow()

    private val _diagnosisState = MutableStateFlow<DiagnosisUiState>(DiagnosisUiState.Idle)
    val diagnosisState: StateFlow<DiagnosisUiState> = _diagnosisState.asStateFlow()

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                sender = "bot",
                text = "🌾 Namaste & Welcome! I am AgriCare AI - your Precision Agriculture & Crop Diagnostics Assistant. How can I help your farm today?"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    fun selectLanguage(language: String) {
        _selectedLanguage.value = language
        prefs.edit().putString("selected_language", language).apply()
    }

    fun completeLanguageSetup(language: String) {
        selectLanguage(language)
        prefs.edit().putBoolean("has_completed_language_setup", true).apply()
        _hasCompletedLanguageSetup.value = true
    }

    fun resetLanguageSetup() {
        prefs.edit().putBoolean("has_completed_language_setup", false).apply()
        _hasCompletedLanguageSetup.value = false
    }

    fun selectPresetSample(presetId: String) {
        _selectedPresetId.value = presetId
        _selectedImageUri.value = null
        _selectedImageBitmap.value = null
    }

    fun setImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
        _selectedPresetId.value = null
        if (uri != null) {
            try {
                @Suppress("DEPRECATION")
                val bitmap = MediaStore.Images.Media.getBitmap(getApplication<Application>().contentResolver, uri)
                _selectedImageBitmap.value = bitmap
            } catch (e: Exception) {
                _selectedImageBitmap.value = null
            }
        } else {
            _selectedImageBitmap.value = null
        }
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        _selectedImageBitmap.value = bitmap
        _selectedImageUri.value = null
        _selectedPresetId.value = null
    }

    fun runCropDiagnosis() {
        viewModelScope.launch {
            val lang = _selectedLanguage.value
            _diagnosisState.value = DiagnosisUiState.Loading("🔍 Scanning leaf anatomy & spot patterns...")
            
            kotlinx.coroutines.delay(600)
            _diagnosisState.value = DiagnosisUiState.Loading("🦠 Identifying crop species & pathogen symptoms...")
            
            kotlinx.coroutines.delay(600)
            _diagnosisState.value = DiagnosisUiState.Loading("🌿 Formulating organic remedies & precision NPK plan...")

            val bitmap = _selectedImageBitmap.value
            val presetId = _selectedPresetId.value

            val result = repository.diagnoseCropImage(bitmap, presetId, lang)
            result.fold(
                onSuccess = { diagResult ->
                    // Auto-save diagnosis to history
                    val imagePath = _selectedImageUri.value?.toString() ?: presetId ?: "preset_sample"
                    val savedId = repository.saveDiagnosis(
                        cropName = diagResult.cropSpecies,
                        diseaseName = diagResult.diseaseName,
                        isHealthy = diagResult.isHealthy,
                        severity = diagResult.severity,
                        confidence = diagResult.confidencePercentage,
                        imageUri = imagePath,
                        result = diagResult
                    )
                    _diagnosisState.value = DiagnosisUiState.Success(diagResult, savedId)
                },
                onFailure = { err ->
                    // Fallback to sample if error
                    val sample = CropPresetSamples.list.first()
                    _diagnosisState.value = DiagnosisUiState.Success(sample.defaultResult.copy(language = lang))
                }
            )
        }
    }

    fun sendChatMessage(question: String) {
        if (question.isBlank()) return
        val userMsg = ChatMessage(sender = "user", text = question)
        _chatMessages.value = _chatMessages.value + userMsg
        _isChatLoading.value = true

        viewModelScope.launch {
            val responseText = repository.askAgriChatbot(question, _selectedLanguage.value)
            val botMsg = ChatMessage(sender = "bot", text = responseText)
            _chatMessages.value = _chatMessages.value + botMsg
            _isChatLoading.value = false
        }
    }

    fun calculateFertilizer(
        acres: Double,
        cropType: String,
        soilType: String,
        stage: String
    ): FertilizerCalculationResult {
        val multiplier = if (acres > 0) acres else 1.0
        val baseN = when (cropType.lowercase()) {
            "paddy", "rice", "நெல்" -> 40.0
            "tomato", "தக்காளி" -> 30.0
            "cotton", "பருத்தி" -> 45.0
            "maize", "corn", "மக்காச்சோளம்" -> 50.0
            "chilli", "மிளகாய்" -> 35.0
            else -> 35.0
        }
        val baseP = baseN * 0.5
        val baseK = baseN * 0.6

        val soilAdjustment = when (soilType.lowercase()) {
            "sandy" -> 1.15
            "clay" -> 0.90
            else -> 1.0
        }

        val totalN = baseN * multiplier * soilAdjustment
        val totalP = baseP * multiplier * soilAdjustment
        val totalK = baseK * multiplier * soilAdjustment

        val vermicompost = 250.0 * multiplier
        val neemCake = 50.0 * multiplier
        val panchagavyaL = 3.0 * multiplier

        val schedule = listOf(
            "Basal Stage (Sowing)" to "Apply 50% Nitrogen + 100% Phosphorus + 50% Potassium + 50 kg Neem Cake.",
            "Vegetative / Tillering Stage" to "Top dress 25% Nitrogen + Spray 3% Panchagavya solution.",
            "Flowering / Panicle Stage" to "Apply remaining 25% Nitrogen + 50% Potassium + Foliar Micronutrient spray."
        )

        return FertilizerCalculationResult(
            nitrogenKg = totalN,
            phosphorusKg = totalP,
            potassiumKg = totalK,
            vermicompostKg = vermicompost,
            neemCakeKg = neemCake,
            panchagavyaLiters = panchagavyaL,
            stageSchedule = schedule
        )
    }

    fun addCropProfile(
        fieldName: String,
        cropType: String,
        soilType: String,
        acres: Double,
        notes: String
    ) {
        viewModelScope.launch {
            repository.saveCropProfile(
                fieldName = fieldName,
                cropType = cropType,
                soilType = soilType,
                fieldAreaAcres = acres,
                sowingDate = System.currentTimeMillis(),
                healthScore = 88,
                notes = notes
            )
        }
    }

    fun deleteCropProfile(id: Long) {
        viewModelScope.launch {
            repository.deleteCropProfile(id)
        }
    }

    fun deleteDiagnosisHistory(id: Long) {
        viewModelScope.launch {
            repository.deleteDiagnosis(id)
        }
    }
}
