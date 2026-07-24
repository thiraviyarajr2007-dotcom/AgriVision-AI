package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.PestControl
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Spoke
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CropDiagnosisResult

@Composable
fun DiagnosisResultCardsList(
    result: CropDiagnosisResult,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card 1: Species & Health Status
        CardSpeciesHeader(result)

        // Card 2: Disease Identification & Symptoms
        CardDiseaseDetails(result)

        // Card 3: Organic Treatments (Eco Priority)
        CardOrganicTreatments(result)

        // Card 4: Chemical Treatments & Safety Warning
        CardChemicalTreatments(result)

        // Card 5: Fertilizer & NPK Schedule
        CardFertilizerPlan(result)

        // Card 6: Irrigation Plan
        CardIrrigationPlan(result)

        // Card 7: Growth & Yield Prediction
        CardGrowthAndYield(result)

        // Card 8: Low-Cost vs Premium Options
        CardCostOptions(result)

        // Card 9: Prevention, Weather & Soil Improvement
        CardPreventionAndSoil(result)
    }
}

@Composable
fun CardSpeciesHeader(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (result.isHealthy) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (result.isHealthy) Icons.Default.CheckCircle else Icons.Default.Warning,
                        contentDescription = null,
                        tint = if (result.isHealthy) Color(0xFF2E7D32) else Color(0xFFC62828),
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = result.cropSpecies,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B3B2B)
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            if (result.isHealthy) Color(0xFFC8E6C9) else Color(0xFFFFCDD2),
                            shape = CircleShape
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (result.isHealthy) "HEALTHY CROP" else "${result.severity.uppercase()} SEVERITY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (result.isHealthy) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("AI Diagnosis Confidence", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text("${result.confidencePercentage}% Match", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("Plant Health Score", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text("${result.growthMonitoring.plantHealthScore}/100", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { result.confidencePercentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = if (result.isHealthy) Color(0xFF4CAF50) else Color(0xFFE53935),
                trackColor = Color.White.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun CardDiseaseDetails(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.BugReport,
                title = "2. Disease & Symptoms Analysis",
                tint = Color(0xFFD97706)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = result.diseaseName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Stage: ${result.diseaseStage}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            if (result.cause.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Cause:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                Text(result.cause, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
            }

            if (result.symptoms.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Detected Symptoms:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                result.symptoms.forEach { symptom ->
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text("• ", fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                        Text(symptom, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            if (result.spreadRisk.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFFF7ED), shape = RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Row {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color(0xFFC2410C), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Spread Risk: ${result.spreadRisk}", style = MaterialTheme.typography.bodySmall, color = Color(0xFF7C2D12))
                    }
                }
            }
        }
    }
}

@Composable
fun CardOrganicTreatments(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.Eco,
                title = "3. Organic Treatments (Eco Priority)",
                tint = Color(0xFF16A34A)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = result.environmentalPriorityNote,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF14532D)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val org = result.organicTreatments
            TreatmentRowItem("Homemade Remedies", org.homemadeRemedies.joinToString("\n• "))
            TreatmentRowItem("Neem Oil Solution", org.neemOil)
            TreatmentRowItem("Panchagavya Bio-Spray", org.panchagavya)
            TreatmentRowItem("Trichoderma Viride", org.trichoderma)
            TreatmentRowItem("Bio-Fertilizers & Compost", org.bioFertilizers.joinToString(", ") + " | " + org.compost)
            TreatmentRowItem("Vermicompost & Cow Dung", org.vermicompost + " | " + org.cowDung)
        }
    }
}

@Composable
fun CardChemicalTreatments(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.Science,
                title = "4. Chemical Treatments & Dosage",
                tint = Color(0xFF0284C7)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (result.chemicalTreatments.items.isEmpty()) {
                Text(
                    text = "No chemical pesticides needed! Crop is healthy or manageable via organic solutions.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF15803D),
                    fontWeight = FontWeight.Medium
                )
            } else {
                result.chemicalTreatments.items.forEach { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .background(Color(0xFFF0F9FF), shape = RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "[${item.category}] ${item.name}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0369A1)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Dosage: ${item.dosage}", style = MaterialTheme.typography.bodySmall)
                        Text("Interval: ${item.sprayInterval}", style = MaterialTheme.typography.bodySmall)
                        Text("Precautions: ${item.safetyPrecautions}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            // Pesticide Warning Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFEF2F2), shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFA855F7).copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = result.chemicalTreatments.safetyWarning,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF991B1B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardFertilizerPlan(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.Agriculture,
                title = "5. Fertilizer & N-P-K Plan",
                tint = Color(0xFF15803D)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val plan = result.fertilizerPlan
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NpkBadge("Nitrogen (N)", "${plan.nitrogenKgPerAcre} kg/acre", Color(0xFF22C55E))
                NpkBadge("Phosphorus (P)", "${plan.phosphorusKgPerAcre} kg/acre", Color(0xFF3B82F6))
                NpkBadge("Potassium (K)", "${plan.potassiumKgPerAcre} kg/acre", Color(0xFFEAB308))
            }

            if (plan.micronutrients.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Micronutrients Required:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    plan.micronutrients.forEach { micro ->
                        SuggestionChip(
                            onClick = {},
                            label = { Text(micro, style = MaterialTheme.typography.bodySmall) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = Color(0xFFECFDF5)
                            )
                        )
                    }
                }
            }

            if (plan.stageSchedule.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Growth Stage Schedule:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                plan.stageSchedule.forEach { sched ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("• ${sched.stageName} (${sched.timingDays}): ", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                        Text(sched.fertilizerRecommendation, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Composable
fun CardIrrigationPlan(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.WaterDrop,
                title = "6. Irrigation & Water Management",
                tint = Color(0xFF0284C7)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val irri = result.irrigation
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Water Volume", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text(irri.waterAmountPerAcre, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("Watering Frequency", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text(irri.frequencyDays, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                }
            }

            if (irri.overwateringWarnings.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEFF6FF), shape = RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Row {
                        Icon(Icons.Default.Opacity, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(irri.overwateringWarnings, style = MaterialTheme.typography.bodySmall, color = Color(0xFF1E40AF))
                    }
                }
            }
        }
    }
}

@Composable
fun CardGrowthAndYield(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.ShowChart,
                title = "7. Growth Tracking & Yield Prediction",
                tint = Color(0xFF7C3AED)
            )

            Spacer(modifier = Modifier.height(12.dp))

            val gm = result.growthMonitoring
            val yp = result.yieldPrediction

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Current Growth Stage", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text(gm.currentStage, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("Next Stage", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text(gm.nextExpectedStage, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))

            Text("Expected Harvest:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            Text(yp.expectedHarvestDate, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Expected Production", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text(yp.expectedProductionQuintalsPerAcre, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                }
                Column {
                    Text("Estimated Market Value", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text(yp.estimatedMarketValue, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                }
            }
        }
    }
}

@Composable
fun CardCostOptions(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.LocalFlorist,
                title = "8. Low-Cost vs Premium Treatment Options",
                tint = Color(0xFF059669)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text("💰 Low-Cost Organic Remedies (Recommended First):", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
            result.costOptions.lowCostOrganicRemedies.forEach { opt ->
                Text("• $opt", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 2.dp))
            }

            if (result.costOptions.premiumTreatmentOptions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text("💎 Premium Biological/Commercial Solutions:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
                result.costOptions.premiumTreatmentOptions.forEach { opt ->
                    Text("• $opt", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 2.dp))
                }
            }
        }
    }
}

@Composable
fun CardPreventionAndSoil(result: CropDiagnosisResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CardSectionHeader(
                icon = Icons.Default.Spoke,
                title = "9. Prevention, Weather & Soil Science",
                tint = Color(0xFF475569)
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (result.weatherAdvice.isNotBlank()) {
                Row {
                    Icon(Icons.Default.Thermostat, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text("Weather Advisory:", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        Text(result.weatherAdvice, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (result.preventionTips.isNotEmpty()) {
                Text("Prevention Tips:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                result.preventionTips.forEach { tip ->
                    Text("• $tip", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 1.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (result.soilImprovement.isNotEmpty()) {
                Text("Soil Improvement & Health:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                result.soilImprovement.forEach { soil ->
                    Text("• $soil", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 1.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (result.cropRotation.isNotEmpty()) {
                Text("Crop Rotation Recommendations:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                result.cropRotation.forEach { rot ->
                    Text("• $rot", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 1.dp))
                }
            }
        }
    }
}

@Composable
fun CardSectionHeader(icon: ImageVector, title: String, tint: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(tint.copy(alpha = 0.15f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun TreatmentRowItem(label: String, content: String) {
    if (content.isNotBlank()) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = Color(0xFF166534))
            Text(content, style = MaterialTheme.typography.bodySmall, color = Color(0xFF14532D))
        }
    }
}

@Composable
fun NpkBadge(label: String, amount: String, badgeColor: Color) {
    Column(
        modifier = Modifier
            .background(badgeColor.copy(alpha = 0.12f), shape = RoundedCornerShape(10.dp))
            .border(1.dp, badgeColor.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = badgeColor, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(2.dp))
        Text(amount, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
    }
}
