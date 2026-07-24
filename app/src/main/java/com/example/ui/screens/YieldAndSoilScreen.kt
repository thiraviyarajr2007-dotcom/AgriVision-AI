package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PestControl
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.SoilTestEntity
import com.example.ui.AdvancedYieldPredictionResult
import com.example.ui.AgriViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YieldAndSoilScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val soilTests by viewModel.allSoilTests.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ShowChart, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ML Yield Prediction")
                    }
                },
                modifier = Modifier.testTag("tab_ml_yield")
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Soil Analysis (${soilTests.size})")
                    }
                },
                modifier = Modifier.testTag("tab_soil_test")
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> YieldPredictionTabContent(viewModel = viewModel)
                1 -> SoilAnalysisTabContent(viewModel = viewModel, soilTests = soilTests)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YieldPredictionTabContent(viewModel: AgriViewModel) {
    var cropName by remember { mutableStateOf("Paddy / Rice") }
    var acresText by remember { mutableStateOf("3.5") }
    var histYieldText by remember { mutableStateOf("24.0") }
    var soilType by remember { mutableStateOf("Red Loamy") }
    var nitrogenLevel by remember { mutableStateOf("Medium") }
    var weatherForecast by remember { mutableStateOf("Favorable / Normal Monsoon") }
    var pestFrequency by remember { mutableStateOf("Low / Zero") }

    var predictionResult by remember { mutableStateOf<AdvancedYieldPredictionResult?>(null) }

    val crops = listOf("Paddy / Rice", "Tomato", "Cotton", "Maize", "Chilli", "Groundnut")
    val soils = listOf("Red Loamy", "Black Cotton", "Alluvial / Loam", "Clay Soil", "Sandy Soil")
    val nLevels = listOf("Low", "Medium", "High")
    val weathers = listOf("Favorable / Normal Monsoon", "Drought / Rain Deficit Risk", "Heavy Rainfall / Flood Risk")
    val pestLevels = listOf("Low / Zero", "Moderate", "Frequent / High Severity")

    var cropExpanded by remember { mutableStateOf(false) }
    var soilExpanded by remember { mutableStateOf(false) }
    var nExpanded by remember { mutableStateOf(false) }
    var weatherExpanded by remember { mutableStateOf(false) }
    var pestExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ShowChart,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Precision ML Yield & Revenue Model",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Considers historical yield, soil reports, long-term weather & pest risks",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Farm Inputs & Environmental Factors", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Crop Dropdown
                        ExposedDropdownMenuBox(
                            expanded = cropExpanded,
                            onExpandedChange = { cropExpanded = !cropExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = cropName,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Crop Type") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cropExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            )
                            ExposedDropdownMenu(
                                expanded = cropExpanded,
                                onDismissRequest = { cropExpanded = false }
                            ) {
                                crops.forEach { c ->
                                    DropdownMenuItem(text = { Text(c) }, onClick = { cropName = c; cropExpanded = false })
                                }
                            }
                        }

                        OutlinedTextField(
                            value = acresText,
                            onValueChange = { acresText = it },
                            label = { Text("Area (Acres)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = histYieldText,
                            onValueChange = { histYieldText = it },
                            label = { Text("Past Yield (Quintals/Acre)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )

                        ExposedDropdownMenuBox(
                            expanded = soilExpanded,
                            onExpandedChange = { soilExpanded = !soilExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = soilType,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Soil Type") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = soilExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            )
                            ExposedDropdownMenu(
                                expanded = soilExpanded,
                                onDismissRequest = { soilExpanded = false }
                            ) {
                                soils.forEach { s ->
                                    DropdownMenuItem(text = { Text(s) }, onClick = { soilType = s; soilExpanded = false })
                                }
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ExposedDropdownMenuBox(
                            expanded = nExpanded,
                            onExpandedChange = { nExpanded = !nExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = nitrogenLevel,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Soil Nitrogen") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = nExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            )
                            ExposedDropdownMenu(
                                expanded = nExpanded,
                                onDismissRequest = { nExpanded = false }
                            ) {
                                nLevels.forEach { n ->
                                    DropdownMenuItem(text = { Text(n) }, onClick = { nitrogenLevel = n; nExpanded = false })
                                }
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = pestExpanded,
                            onExpandedChange = { pestExpanded = !pestExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = pestFrequency,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Pest/Disease Risk") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = pestExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            )
                            ExposedDropdownMenu(
                                expanded = pestExpanded,
                                onDismissRequest = { pestExpanded = false }
                            ) {
                                pestLevels.forEach { p ->
                                    DropdownMenuItem(text = { Text(p) }, onClick = { pestFrequency = p; pestExpanded = false })
                                }
                            }
                        }
                    }

                    // Weather dropdown
                    ExposedDropdownMenuBox(
                        expanded = weatherExpanded,
                        onExpandedChange = { weatherExpanded = !weatherExpanded }
                    ) {
                        OutlinedTextField(
                            value = weatherForecast,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Long-Term Weather Forecast Trend") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = weatherExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = weatherExpanded,
                            onDismissRequest = { weatherExpanded = false }
                        ) {
                            weathers.forEach { w ->
                                DropdownMenuItem(text = { Text(w) }, onClick = { weatherForecast = w; weatherExpanded = false })
                            }
                        }
                    }

                    Button(
                        onClick = {
                            val acres = acresText.toDoubleOrNull() ?: 1.0
                            val hist = histYieldText.toDoubleOrNull() ?: 24.0
                            predictionResult = viewModel.calculateAdvancedYieldML(
                                cropName = cropName,
                                acres = acres,
                                historicalYieldQuintalsPerAcre = hist,
                                soilType = soilType,
                                nitrogenLevel = nitrogenLevel,
                                weatherForecast = weatherForecast,
                                pastPestFrequency = pestFrequency
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("btn_run_yield_ml"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.ShowChart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Calculate ML Yield & Revenue Prediction", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        predictionResult?.let { res ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "📊 ML Yield & Profit Prediction Result",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Predicted Yield", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                                Text("${res.predictedYieldPerAcre} Q/Acre", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Text("Total: ${res.totalExpectedYieldQuintals} Quintals", style = MaterialTheme.typography.bodySmall)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Estimated Net Profit", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                                Text("₹${res.estimatedNetProfitRs.toInt()}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                Text("Revenue: ₹${res.estimatedRevenueRs.toInt()}", style = MaterialTheme.typography.bodySmall)
                            }
                        }

                        // Harvest Estimate
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Harvest Timeline: ${res.harvestDateEstimate}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                        Text("ML Risk & Impact Breakdown:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        Text("• ${res.soilImpactSummary}", style = MaterialTheme.typography.bodySmall)
                        Text("• ${res.weatherForecastImpact}", style = MaterialTheme.typography.bodySmall)
                        Text("• ${res.diseaseRiskImpact}", style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("💡 Actionable AI Suggestions to Increase Yield:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        res.actionableSuggestions.forEach { sug ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp).padding(top = 2.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(sug, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SoilAnalysisTabContent(
    viewModel: AgriViewModel,
    soilTests: List<SoilTestEntity>
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        if (soilTests.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(48.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No Soil Tests Logged yet.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(soilTests) { test ->
                    SoilTestCard(test = test)
                }
            }
        }

        Button(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("btn_log_soil_test"),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Log Soil Test Report", fontWeight = FontWeight.Bold)
        }

        if (showAddDialog) {
            AddSoilTestDialog(
                onDismiss = { showAddDialog = false },
                onSubmit = { farmName, soilType, ph, n, p, k, oc ->
                    viewModel.saveNewSoilTest(farmName, soilType, ph, n, p, k, oc)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun SoilTestCard(test: SoilTestEntity) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val dateStr = dateFormat.format(Date(test.timestamp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Science, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(test.farmName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
                Text(dateStr, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Soil Type", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text(test.soilType, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("pH Level", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text("${test.phLevel} (${if (test.phLevel in 6.0..7.5) "Optimal" else "Acidic/Alkaline"})", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Column {
                    Text("Organic Carbon", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Text("${test.organicCarbon}%", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("NPK Nutrient Ratings:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("• Nitrogen (N): ${test.nitrogenRating}", style = MaterialTheme.typography.bodySmall)
                Text("• Phosphorus (P): ${test.phosphorusRating}", style = MaterialTheme.typography.bodySmall)
                Text("• Potassium (K): ${test.potassiumRating}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun AddSoilTestDialog(
    onDismiss: () -> Unit,
    onSubmit: (farmName: String, soilType: String, ph: Double, n: String, p: String, k: String, oc: Double) -> Unit
) {
    var farmName by remember { mutableStateOf("North Block Plot 1") }
    var soilType by remember { mutableStateOf("Red Loamy") }
    var phText by remember { mutableStateOf("6.8") }
    var nRating by remember { mutableStateOf("Medium (280 kg/ha)") }
    var pRating by remember { mutableStateOf("High (42 kg/ha)") }
    var kRating by remember { mutableStateOf("Medium (190 kg/ha)") }
    var ocText by remember { mutableStateOf("0.65") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Soil Analysis Test", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = farmName,
                    onValueChange = { farmName = it },
                    label = { Text("Field / Farm Block Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = soilType,
                    onValueChange = { soilType = it },
                    label = { Text("Soil Texture / Type") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = phText,
                        onValueChange = { phText = it },
                        label = { Text("pH Level") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = ocText,
                        onValueChange = { ocText = it },
                        label = { Text("Organic Carbon %") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = nRating,
                    onValueChange = { nRating = it },
                    label = { Text("Nitrogen (N)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pRating,
                    onValueChange = { pRating = it },
                    label = { Text("Phosphorus (P)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = kRating,
                    onValueChange = { kRating = it },
                    label = { Text("Potassium (K)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val ph = phText.toDoubleOrNull() ?: 6.5
                val oc = ocText.toDoubleOrNull() ?: 0.5
                onSubmit(farmName, soilType, ph, nRating, pRating, kRating, oc)
            }) {
                Text("Save Soil Test")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
