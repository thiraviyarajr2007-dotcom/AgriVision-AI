package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.AgriViewModel
import com.example.ui.FertilizerCalculationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorsScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    var areaInput by remember { mutableStateOf("1.0") }
    var selectedCrop by remember { mutableStateOf("Paddy (Rice)") }
    var selectedSoil by remember { mutableStateOf("Loam") }
    var selectedStage by remember { mutableStateOf("Vegetative Stage") }

    var calcResult by remember { mutableStateOf<FertilizerCalculationResult?>(null) }

    val cropsList = listOf("Paddy (Rice)", "Tomato", "Cotton", "Maize (Corn)", "Chilli", "Sugarcane", "Wheat")
    val soilList = listOf("Loam (Ideal)", "Clay (Heavy)", "Sandy (Light)", "Alluvial", "Red Soil", "Black Cotton Soil")
    val stageList = listOf("Basal (Sowing)", "Vegetative Stage", "Flowering Stage", "Fruiting / Panicle Stage")

    var cropExpanded by remember { mutableStateOf(false) }
    var soilExpanded by remember { mutableStateOf(false) }
    var stageExpanded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Calculator Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Precision Fertilizer & Water Calculator",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Calculate exact N-P-K kilograms, organic substitutes (Vermicompost, Panchagavya), and growth stage schedules.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Calculator Inputs Form
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Farm Field Details",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                // Field Area (Acres)
                OutlinedTextField(
                    value = areaInput,
                    onValueChange = { areaInput = it },
                    label = { Text("Field Area (in Acres)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("area_acres_input"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Select Crop Dropdown
                ExposedDropdownMenuBox(
                    expanded = cropExpanded,
                    onExpandedChange = { cropExpanded = !cropExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCrop,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Crop Species") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = cropExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = cropExpanded,
                        onDismissRequest = { cropExpanded = false }
                    ) {
                        cropsList.forEach { crop ->
                            DropdownMenuItem(
                                text = { Text(crop) },
                                onClick = {
                                    selectedCrop = crop
                                    cropExpanded = false
                                }
                            )
                        }
                    }
                }

                // Select Soil Type Dropdown
                ExposedDropdownMenuBox(
                    expanded = soilExpanded,
                    onExpandedChange = { soilExpanded = !soilExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedSoil,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Soil Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = soilExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = soilExpanded,
                        onDismissRequest = { soilExpanded = false }
                    ) {
                        soilList.forEach { soil ->
                            DropdownMenuItem(
                                text = { Text(soil) },
                                onClick = {
                                    selectedSoil = soil
                                    soilExpanded = false
                                }
                            )
                        }
                    }
                }

                // Select Growth Stage Dropdown
                ExposedDropdownMenuBox(
                    expanded = stageExpanded,
                    onExpandedChange = { stageExpanded = !stageExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedStage,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Current Growth Stage") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = stageExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = stageExpanded,
                        onDismissRequest = { stageExpanded = false }
                    ) {
                        stageList.forEach { st ->
                            DropdownMenuItem(
                                text = { Text(st) },
                                onClick = {
                                    selectedStage = st
                                    stageExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {
                        val acres = areaInput.toDoubleOrNull() ?: 1.0
                        calcResult = viewModel.calculateFertilizer(acres, selectedCrop, selectedSoil, selectedStage)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("calculate_fertilizer_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Calculate, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CALCULATE DOSAGE & SCHEDULE", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Calculation Results Display
        calcResult?.let { res ->
            AnimatedVisibility(visible = true) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Chemical NPK Requirements
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Eco, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Calculated Pure N-P-K Nutrients", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                NpkBadge("Nitrogen (N)", "%.1f kg".format(res.nitrogenKg), Color(0xFF22C55E))
                                NpkBadge("Phosphorus (P)", "%.1f kg".format(res.phosphorusKg), Color(0xFF3B82F6))
                                NpkBadge("Potassium (K)", "%.1f kg".format(res.potassiumKg), Color(0xFFEAB308))
                            }
                        }
                    }

                    // Organic Equivalent Substitutes
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Eco, contentDescription = null, tint = Color(0xFF16A34A))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("🌿 Organic Bio-Input Alternatives", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF14532D))
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text("• Vermicompost: %.0f kg".format(res.vermicompostKg), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            Text("• Neem Cake (Soil Conditioner): %.0f kg".format(res.neemCakeKg), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            Text("• Panchagavya (3%% Spray): %.1f Liters".format(res.panchagavyaLiters), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            Text("• Jeevamrutham Liquid: 200 Liters / acre with irrigation water.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    }

                    // Stage-wise Application Schedule
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color(0xFF0284C7))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Stage-Wise Application Schedule", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            res.stageSchedule.forEach { (stg, desc) ->
                                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Text(stg, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    Text(desc, style = MaterialTheme.typography.bodySmall)
                                }
                                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray.copy(alpha = 0.5f))
                            }
                        }
                    }
                }
            }
        }
    }
}
