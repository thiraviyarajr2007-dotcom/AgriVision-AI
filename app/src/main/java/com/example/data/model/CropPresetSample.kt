package com.example.data.model

data class CropPresetSample(
    val id: String,
    val cropName: String,
    val conditionTitle: String,
    val isHealthy: Boolean,
    val description: String,
    val defaultResult: CropDiagnosisResult
)

object CropPresetSamples {
    val list = listOf(
        CropPresetSample(
            id = "rice_blast",
            cropName = "Paddy (Rice) / நெல் / धान / వరి / நெல்",
            conditionTitle = "Rice Blast (Fungal Disease)",
            isHealthy = false,
            description = "Diamond-shaped leaf spots with grey centers caused by Pyricularia oryzae.",
            defaultResult = CropDiagnosisResult(
                cropSpecies = "Paddy (Oryza sativa) / நெல்",
                isHealthy = false,
                diseaseName = "Rice Blast (Pyricularia oryzae) / நெல் பழ நோய்",
                confidencePercentage = 96,
                diseaseStage = "Active Tillering Stage",
                severity = "High",
                cause = "Fungal infection triggered by high relative humidity (>90%), leaf wetness, and excess Nitrogen fertilizer.",
                symptoms = listOf(
                    "Spindle/diamond-shaped lesions on leaves with brown borders and grey centers.",
                    "Neck rot and collar rot leading to empty white heads (deadhearts).",
                    "Lesions enlarge rapidly and coalesce, causing leaf desiccation."
                ),
                spreadRisk = "High. Spores spread rapidly by wind and dew droplets across neighboring paddy fields.",
                organicTreatments = OrganicTreatments(
                    homemadeRemedies = listOf(
                        "Spray cow milk & sour buttermilk solution (1 Liter sour buttermilk in 10 Liters water).",
                        "Apply fermented wood ash water mixture on leaf canopy."
                    ),
                    bioFertilizers = listOf("Azospirillum and Phosphobacteria seed & root treatment (2 kg/acre)."),
                    neemOil = "Spray 5% Neem Seed Kernel Extract (NSKE) or Neem Oil 10,000 ppm at 5ml/Liter water.",
                    compost = "Apply well-decomposed farmyard manure enriched with Trichoderma (250 kg/acre).",
                    vermicompost = "Apply 500 kg/acre vermicompost during final land preparation.",
                    cowDung = "Spray Panchagavya 3% solution (30 ml in 1 Liter water) at 15-day intervals.",
                    panchagavya = "Foliar spray of 3% Panchagavya solution during early morning or late evening.",
                    trichoderma = "Soil application of Trichoderma viride (2.5 kg mixed with 100 kg compost/acre).",
                    bioPesticides = listOf("Pseudomonas fluorescens foliar spray @ 10g/Liter water.")
                ),
                chemicalTreatments = ChemicalTreatments(
                    items = listOf(
                        ChemicalTreatmentItem(
                            category = "Fungicide",
                            name = "Tricyclazole 75% WP (or Isoprothiolane 40% EC)",
                            dosage = "0.6 g/Liter water (120g/acre in 200L water)",
                            sprayInterval = "Repeat spray after 12–14 days if wet weather continues.",
                            safetyPrecautions = "Wear mask, gloves, protective rubber boots. Keep livestock away for 7 days."
                        ),
                        ChemicalTreatmentItem(
                            category = "Fungicide (Alternative)",
                            name = "Azoxystrobin 18.2% + Difenoconazole 11.4% SC",
                            dosage = "1 ml/Liter water",
                            sprayInterval = "Single application at neck emergence stage.",
                            safetyPrecautions = "Toxic to aquatic life. Do not drain spray washings into fish ponds."
                        )
                    ),
                    safetyWarning = "⚠️ CRITICAL SAFETY WARNING: Chemical fungicides must be handled with protective gear. Do not exceed recommended dosage. Keep children and animals away during spraying."
                ),
                fertilizerPlan = FertilizerPlan(
                    nitrogenKgPerAcre = 40.0,
                    phosphorusKgPerAcre = 20.0,
                    potassiumKgPerAcre = 25.0,
                    micronutrients = listOf("Zinc Sulphate 21% @ 10 kg/acre", "Gypsum @ 100 kg/acre"),
                    stageSchedule = listOf(
                        StageScheduleItem("Basal Stage", "At planting", "50% N + 100% P + 50% K + Zinc Sulphate"),
                        StageScheduleItem("Active Tillering", "21-25 DAP", "25% N split application"),
                        StageScheduleItem("Panicle Initiation", "40-45 DAP", "25% N + 50% K split application")
                    )
                ),
                irrigation = IrrigationPlan(
                    waterAmountPerAcre = "Maintain 2-3 cm standing water layer during tillering.",
                    frequencyDays = "Alternate wetting and drying (AWD) every 3-5 days.",
                    overwateringWarnings = "Avoid stagnant deep water (>5 cm) as high humidity aggravates fungal blast."
                ),
                growthMonitoring = GrowthMonitoring(
                    currentStage = "Active Tillering (30-35 DAP)",
                    nextExpectedStage = "Panicle Initiation (45-50 DAP)",
                    growthScore = 72,
                    plantHealthScore = 65
                ),
                yieldPrediction = YieldPrediction(
                    expectedHarvestDate = "45 Days from now",
                    expectedProductionQuintalsPerAcre = "22 - 25 Quintals / Acre",
                    estimatedMarketValue = "₹48,000 - ₹55,000 per acre"
                ),
                preventionTips = listOf(
                    "Use blast-resistant paddy varieties like CO-51, ADT-43, or ASD-16.",
                    "Avoid excessive top-dressing of Urea nitrogen fertilizer.",
                    "Treat seeds with Pseudomonas fluorescens (10g/kg seed) before sowing.",
                    "Maintain clean field bunds and destroy wild host grasses."
                ),
                weatherAdvice = "High cloud cover and evening humidity predicted. Postpone nitrogen top-dressing until weather clears.",
                pestPrevention = listOf("Monitor for Brown Plant Hopper (BPH) and Stem Borer alongside blast."),
                soilImprovement = listOf(
                    "Incorporate paddy straw with Trichoderma to enhance organic carbon.",
                    "Apply Silica solubilizing bacteria to strengthen leaf cuticle epidermal cells."
                ),
                cropRotation = listOf("Rotate with Blackgram, Cowpea, or Green manure (Dhaincha/Sunnhemp) in summer."),
                costOptions = CostOptions(
                    lowCostOrganicRemedies = listOf(
                        "Panchagavya 3% spray (Cost: ~₹150/acre)",
                        "Sour buttermilk + Milk spray (Cost: ~₹100/acre)",
                        "Pseudomonas fluorescens (Cost: ~₹180/acre)"
                    ),
                    premiumTreatmentOptions = listOf(
                        "Azoxystrobin + Difenoconazole systemic fungicide (Cost: ~₹750/acre)",
                        "Tricyclazole 75% WP + Bio-silica booster (Cost: ~₹600/acre)"
                    )
                ),
                environmentalPriorityNote = "🌿 Environment First: Organic biological agents (Pseudomonas & Trichoderma) protect beneficial soil microbes while effectively suppressing blast fungal spores."
            )
        ),
        CropPresetSample(
            id = "tomato_blight",
            cropName = "Tomato / தக்காளி / टमाटर / టమోటా / தக்காளி",
            conditionTitle = "Tomato Early Blight (Alternaria solani)",
            isHealthy = false,
            description = "Concentric target-like rings on lower foliage with yellow halo margins.",
            defaultResult = CropDiagnosisResult(
                cropSpecies = "Tomato (Solanum lycopersicum)",
                isHealthy = false,
                diseaseName = "Early Blight (Alternaria solani)",
                confidencePercentage = 94,
                diseaseStage = "Flowering & Fruiting Stage",
                severity = "Medium",
                cause = "Fungal pathogen survival in infected crop debris, favored by warm temperatures (24-29°C) and heavy dew.",
                symptoms = listOf(
                    "Dark brown spot lesions with characteristic concentric target rings on older bottom leaves.",
                    "Yellowing halo surrounding leaf spots.",
                    "Sunken brown collar rot on stems and dark leathery rot at stem end of fruit."
                ),
                spreadRisk = "Medium-High. Fungal spores splash onto lower leaves via rainfall or overhead sprinkler irrigation.",
                organicTreatments = OrganicTreatments(
                    homemadeRemedies = listOf(
                        "Baking soda spray (1 tbsp baking soda + 1 tsp vegetable oil + 1 tsp liquid soap in 4L water).",
                        "Garlic and Chili extract spray."
                    ),
                    bioFertilizers = listOf("VAM (Vesicular Arbuscular Mycorrhiza) @ 5 kg/acre."),
                    neemOil = "Neem oil 10,000 ppm @ 3-5 ml/L water sprayed every 7 days.",
                    compost = "Mulch soil with clean straw or compost to prevent soil-to-leaf rain splash.",
                    vermicompost = "Vermicompost tea foliar spray.",
                    cowDung = "Fermented cow urine spray diluted 1:10 with water.",
                    panchagavya = "Panchagavya 3% foliar spray.",
                    trichoderma = "Trichoderma harzianum soil drenching around plant base.",
                    bioPesticides = listOf("Bacillus subtilis bio-fungicide @ 5g/Liter water.")
                ),
                chemicalTreatments = ChemicalTreatments(
                    items = listOf(
                        ChemicalTreatmentItem(
                            category = "Fungicide",
                            name = "Mancozeb 75% WP (or Copper Oxychloride 50% WP)",
                            dosage = "2g / Liter water",
                            sprayInterval = "Spray every 10-12 days.",
                            safetyPrecautions = "Observe 7-day pre-harvest interval (PHI) before picking tomatoes."
                        )
                    )
                ),
                fertilizerPlan = FertilizerPlan(
                    nitrogenKgPerAcre = 30.0,
                    phosphorusKgPerAcre = 25.0,
                    potassiumKgPerAcre = 35.0,
                    micronutrients = listOf("Calcium Nitrate @ 5 kg/acre (prevents blossom end rot)", "Boron 20% @ 1g/L"),
                    stageSchedule = listOf(
                        StageScheduleItem("Flowering", "30-40 DAP", "19-19-19 NPK + Boron spray"),
                        StageScheduleItem("Fruiting", "50-70 DAP", "13-0-45 Potash + Calcium Nitrate spray")
                    )
                ),
                irrigation = IrrigationPlan(
                    waterAmountPerAcre = "Drip irrigation 4,000 - 6,000 Liters/day based on soil moisture.",
                    frequencyDays = "Daily or every alternate day via drip.",
                    overwateringWarnings = "Never use overhead hose spraying; wet foliage promotes rapid Alternaria fungal spore germination."
                ),
                growthMonitoring = GrowthMonitoring(
                    currentStage = "Early Fruiting (50 DAP)",
                    nextExpectedStage = "Peak Harvest (70-90 DAP)",
                    growthScore = 78,
                    plantHealthScore = 70
                ),
                yieldPrediction = YieldPrediction(
                    expectedHarvestDate = "25 Days from now",
                    expectedProductionQuintalsPerAcre = "180 - 220 Quintals / Acre",
                    estimatedMarketValue = "₹1,80,000 - ₹2,50,000 per acre"
                ),
                preventionTips = listOf(
                    "Stake tomato plants with bamboo poles to keep leaves off wet soil.",
                    "Prune lower infected sucker branches up to 30 cm from ground level.",
                    "Maintain proper row spacing (60cm x 45cm) for air ventilation."
                ),
                weatherAdvice = "Intermittent rains expected. Apply protective copper or organic baking soda spray before rainfall.",
                pestPrevention = listOf("Install yellow sticky traps for Whiteflies and Helicoverpa fruit borer pheromone traps."),
                soilImprovement = listOf("Apply agricultural lime or dolomite if soil pH is below 6.0 to enhance Calcium absorption."),
                cropRotation = listOf("Do NOT plant Solanaceous crops (Potato, Eggplant, Pepper) after Tomato. Rotate with Maize or Beans."),
                costOptions = CostOptions(
                    lowCostOrganicRemedies = listOf(
                        "Baking soda + Neem oil spray (~₹120/acre)",
                        "Pruning lower leaves & straw mulching (~₹80/acre)"
                    ),
                    premiumTreatmentOptions = listOf(
                        "Azoxystrobin + Mancozeb protective complex (~₹500/acre)"
                    )
                ),
                environmentalPriorityNote = "🌿 Soil Health: Drip irrigation & organic straw mulch eliminate water splashing and conserve groundwater."
            )
        ),
        CropPresetSample(
            id = "cotton_curl",
            cropName = "Cotton / பருத்தி / कपास / పత్తి / పరుత్తి",
            conditionTitle = "Cotton Leaf Curl Virus (CLCuV)",
            isHealthy = false,
            description = "Upward curling of leaves with thickened vein enations, transmitted by Whitefly.",
            defaultResult = CropDiagnosisResult(
                cropSpecies = "Cotton (Gossypium hirsutum)",
                isHealthy = false,
                diseaseName = "Cotton Leaf Curl Virus (transmitted by Bemisia tabaci Whitefly)",
                confidencePercentage = 92,
                diseaseStage = "Squaring / Vegetative Growth Stage",
                severity = "Medium",
                cause = "Begomovirus transmitted persistently by Whitefly vectors during warm dry weather.",
                symptoms = listOf(
                    "Upward and downward cupping and curling of young tender leaves.",
                    "Thickening of leaf veins on lower surface with leaf-like outgrowth enations.",
                    "Stunted plant growth and reduced boll formation."
                ),
                spreadRisk = "High during dry spells when Whitefly insect population surges.",
                organicTreatments = OrganicTreatments(
                    homemadeRemedies = listOf(
                        "Yellow Sticky Traps (20 traps per acre) to catch adult whiteflies.",
                        "Fermented sour milk + Asafoetida (Hing) spray."
                    ),
                    bioFertilizers = listOf("Azotobacter & PSB soil application."),
                    neemOil = "Neem oil 10,000 ppm @ 5 ml/Liter water with 1 ml liquid soap sticker.",
                    compost = "Well decomposed manure with Neem cake (100 kg/acre).",
                    vermicompost = "Vermicompost 300 kg/acre.",
                    cowDung = "Agniastra or Bramastra indigenous botanical formulation.",
                    panchagavya = "Panchagavya 3% foliar application.",
                    trichoderma = "Trichoderma soil application.",
                    bioPesticides = listOf("Verticillium lecanii (Lecanicillium) @ 5g/Liter water for whitefly nymph control.")
                ),
                chemicalTreatments = ChemicalTreatments(
                    items = listOf(
                        ChemicalTreatmentItem(
                            category = "Insecticide (Vector Control)",
                            name = "Diafenthiuron 50% WP (or Pyriproxyfen 10% EC)",
                            dosage = "1.25 g/Liter water",
                            sprayInterval = "Spray at 10-day intervals if whitefly count exceeds 5 per leaf.",
                            safetyPrecautions = "Do not spray during honeybee foraging hours in early morning."
                        )
                    )
                ),
                fertilizerPlan = FertilizerPlan(
                    nitrogenKgPerAcre = 45.0,
                    phosphorusKgPerAcre = 20.0,
                    potassiumKgPerAcre = 20.0,
                    micronutrients = listOf("Magnesium Sulphate @ 5 kg/acre", "Micronutrient mix foliar spray"),
                    stageSchedule = listOf(
                        StageScheduleItem("Vegetative", "30 DAP", "Urea split + Neem cake"),
                        StageScheduleItem("Squaring", "60 DAP", "NPK 19-19-19 + Boron")
                    )
                ),
                irrigation = IrrigationPlan(
                    waterAmountPerAcre = "Furrow or Drip irrigation every 7-10 days depending on soil type.",
                    frequencyDays = "Every 7-10 days.",
                    overwateringWarnings = "Avoid moisture stress followed by sudden heavy watering as it causes boll shedding."
                ),
                growthMonitoring = GrowthMonitoring(
                    currentStage = "Squaring Stage (45 DAP)",
                    nextExpectedStage = "Boll Development (75 DAP)",
                    growthScore = 75,
                    plantHealthScore = 68
                ),
                yieldPrediction = YieldPrediction(
                    expectedHarvestDate = "60 Days from now",
                    expectedProductionQuintalsPerAcre = "10 - 12 Quintals / Acre",
                    estimatedMarketValue = "₹70,000 - ₹85,000 per acre"
                ),
                preventionTips = listOf(
                    "Erect Yellow Sticky Traps early at 15 DAP to destroy vector whiteflies.",
                    "Sow boundary crops like Sorghum, Maize, or Bajra as physical whitefly barriers.",
                    "Remove weed hosts like Abutilon and Solanum from field margins."
                ),
                weatherAdvice = "Hot dry weather favors whiteflies. Deploy yellow sticky traps and apply bio-insecticide immediately.",
                pestPrevention = listOf("Keep vigilant for Pink Bollworm (install Pheromone traps @ 4/acre)."),
                soilImprovement = listOf("Apply Neem cake 100 kg/acre to reduce soil-borne nematodes and pests."),
                cropRotation = listOf("Rotate with Wheat, Mustard, or Pulses. Avoid consecutive cotton cropping."),
                costOptions = CostOptions(
                    lowCostOrganicRemedies = listOf(
                        "Yellow Sticky Traps + Neem Oil spray (~₹150/acre)",
                        "Verticillium lecanii bio-agent (~₹200/acre)"
                    ),
                    premiumTreatmentOptions = listOf(
                        "Diafenthiuron systemic vector control (~₹550/acre)"
                    )
                ),
                environmentalPriorityNote = "🌿 Eco-Friendly Focus: Yellow sticky traps and entomopathogenic fungi (Verticillium) eliminate whiteflies safely without harming beneficial predatory ladybird beetles."
            )
        ),
        CropPresetSample(
            id = "healthy_paddy",
            cropName = "Paddy (Rice) - Healthy / ஆரோக்கியமான நெல் / स्वस्थ धान",
            conditionTitle = "Healthy Condition - Optimal Crop Health",
            isHealthy = true,
            description = "Vibrant deep green foliage, robust tiller strength, no disease or pest symptoms.",
            defaultResult = CropDiagnosisResult(
                cropSpecies = "Paddy (Oryza sativa) / நெல்",
                isHealthy = true,
                diseaseName = "None / Healthy Condition (ஆரோக்கியமான பயிர்)",
                confidencePercentage = 98,
                diseaseStage = "Mid-Tillering Stage",
                severity = "Low",
                cause = "Excellent agronomic management, balanced nutrition, and optimal soil moisture.",
                symptoms = listOf(
                    "Foliage is vibrant emerald green with zero leaf spot lesions or insect damage.",
                    "Sturdy stems with deep healthy white root establishment."
                ),
                spreadRisk = "None.",
                organicTreatments = OrganicTreatments(
                    homemadeRemedies = listOf("Maintain routine Panchagavya 3% spray as growth promoter."),
                    bioFertilizers = listOf("Azospirillum and Phosphobacteria top-dressing with compost."),
                    neemOil = "Preventative Neem oil spray 3ml/L every 21 days.",
                    compost = "Maintain organic compost mulch.",
                    vermicompost = "Apply 100 kg/acre as top dressing during panicle initiation.",
                    cowDung = "Jeevamrutham liquid bio-fertilizer application with irrigation water.",
                    panchagavya = "Foliar spray at 30 DAP and 60 DAP to boost tillering count.",
                    trichoderma = "Preventative soil drenching.",
                    bioPesticides = listOf("Pseudomonas fluorescens 5g/L preventative spray.")
                ),
                chemicalTreatments = ChemicalTreatments(
                    items = emptyList(),
                    safetyWarning = "No chemical pesticides required! Your crop is currently 100% healthy."
                ),
                fertilizerPlan = FertilizerPlan(
                    nitrogenKgPerAcre = 35.0,
                    phosphorusKgPerAcre = 15.0,
                    potassiumKgPerAcre = 20.0,
                    micronutrients = listOf("Zinc Sulphate 21% @ 5 kg/acre"),
                    stageSchedule = listOf(
                        StageScheduleItem("Panicle Initiation", "45 DAP", "20% N + 50% Potash (MOP) top dressing"),
                        StageScheduleItem("Grain Filling", "75 DAP", "1% Potassium Nitrate (13-0-45) foliar spray")
                    )
                ),
                irrigation = IrrigationPlan(
                    waterAmountPerAcre = "Keep 2-5 cm standing water during panicle formation.",
                    frequencyDays = "Water every 3-4 days.",
                    overwateringWarnings = "Drain field 10 days before final harvest to ensure uniform ripening."
                ),
                growthMonitoring = GrowthMonitoring(
                    currentStage = "Tillering Stage (35 DAP)",
                    nextExpectedStage = "Panicle Emergence (60 DAP)",
                    growthScore = 95,
                    plantHealthScore = 98
                ),
                yieldPrediction = YieldPrediction(
                    expectedHarvestDate = "50 Days from now",
                    expectedProductionQuintalsPerAcre = "26 - 30 Quintals / Acre",
                    estimatedMarketValue = "₹58,000 - ₹68,000 per acre"
                ),
                preventionTips = listOf(
                    "Continue alternate wetting and drying irrigation to build strong root anchors.",
                    "Keep field free of weeds along bunds.",
                    "Apply Zinc sulphate if leaves show light yellowing."
                ),
                weatherAdvice = "Favorable weather conditions expected over the next 7 days. Ideal window for organic Jeevamrutham application.",
                pestPrevention = listOf("Install light traps at night to monitor stem borer moth emergence."),
                soilImprovement = listOf("Incorporate Azolla bio-fertilizer in standing water to boost soil Nitrogen naturally."),
                cropRotation = listOf("Plan summer legume crop (Blackgram/Sesame) after harvest."),
                costOptions = CostOptions(
                    lowCostOrganicRemedies = listOf("Jeevamrutham liquid fertilizer (Cost: ~₹50/acre)"),
                    premiumTreatmentOptions = listOf("Bio-stimulant amino acid foliar spray (Cost: ~₹300/acre)")
                ),
                environmentalPriorityNote = "🌟 Outstanding Eco-Farming: Healthy natural ecosystem preserved with zero chemical runoff!"
            )
        )
    )
}
