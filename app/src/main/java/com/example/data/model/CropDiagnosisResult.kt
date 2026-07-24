package com.example.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrganicTreatments(
    val homemadeRemedies: List<String> = emptyList(),
    val bioFertilizers: List<String> = emptyList(),
    val neemOil: String = "",
    val compost: String = "",
    val vermicompost: String = "",
    val cowDung: String = "",
    val panchagavya: String = "",
    val trichoderma: String = "",
    val bioPesticides: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class ChemicalTreatmentItem(
    val category: String = "", // Fungicide, Insecticide, Fertilizer
    val name: String = "",
    val dosage: String = "",
    val sprayInterval: String = "",
    val safetyPrecautions: String = ""
)

@JsonClass(generateAdapter = true)
data class ChemicalTreatments(
    val items: List<ChemicalTreatmentItem> = emptyList(),
    val safetyWarning: String = "⚠️ WARNING: Always wear protective masks, gloves, and long sleeves when applying chemical pesticides. Never spray against wind direction or near water bodies."
)

@JsonClass(generateAdapter = true)
data class StageScheduleItem(
    val stageName: String = "",
    val timingDays: String = "",
    val fertilizerRecommendation: String = ""
)

@JsonClass(generateAdapter = true)
data class FertilizerPlan(
    val nitrogenKgPerAcre: Double = 0.0,
    val phosphorusKgPerAcre: Double = 0.0,
    val potassiumKgPerAcre: Double = 0.0,
    val micronutrients: List<String> = emptyList(),
    val stageSchedule: List<StageScheduleItem> = emptyList()
)

@JsonClass(generateAdapter = true)
data class IrrigationPlan(
    val waterAmountPerAcre: String = "",
    val frequencyDays: String = "",
    val overwateringWarnings: String = ""
)

@JsonClass(generateAdapter = true)
data class GrowthMonitoring(
    val currentStage: String = "",
    val nextExpectedStage: String = "",
    val growthScore: Int = 80, // 0-100
    val plantHealthScore: Int = 80 // 0-100
)

@JsonClass(generateAdapter = true)
data class YieldPrediction(
    val expectedHarvestDate: String = "",
    val expectedProductionQuintalsPerAcre: String = "",
    val estimatedMarketValue: String = ""
)

@JsonClass(generateAdapter = true)
data class CostOptions(
    val lowCostOrganicRemedies: List<String> = emptyList(),
    val premiumTreatmentOptions: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class CropDiagnosisResult(
    val cropSpecies: String = "Unknown Crop",
    val isHealthy: Boolean = false,
    val diseaseName: String = "None / Healthy",
    val confidencePercentage: Int = 90,
    val diseaseStage: String = "Vegetative Stage",
    val severity: String = "Low", // Low, Medium, High
    val cause: String = "",
    val symptoms: List<String> = emptyList(),
    val spreadRisk: String = "",
    val organicTreatments: OrganicTreatments = OrganicTreatments(),
    val chemicalTreatments: ChemicalTreatments = ChemicalTreatments(),
    val fertilizerPlan: FertilizerPlan = FertilizerPlan(),
    val irrigation: IrrigationPlan = IrrigationPlan(),
    val growthMonitoring: GrowthMonitoring = GrowthMonitoring(),
    val yieldPrediction: YieldPrediction = YieldPrediction(),
    val preventionTips: List<String> = emptyList(),
    val weatherAdvice: String = "",
    val pestPrevention: List<String> = emptyList(),
    val soilImprovement: List<String> = emptyList(),
    val cropRotation: List<String> = emptyList(),
    val costOptions: CostOptions = CostOptions(),
    val environmentalPriorityNote: String = "🌿 Priority Note: Always prioritize eco-friendly organic bio-inputs first to preserve soil microbes and beneficial insects.",
    val language: String = "English"
)
