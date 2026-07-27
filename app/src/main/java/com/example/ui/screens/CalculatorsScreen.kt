package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AgriViewModel
import com.example.ui.FertilizerCalculationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorsScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    var acresValue by remember { mutableFloatStateOf(2.5f) }
    var selectedCrop by remember { mutableStateOf("Paddy (Rice)") }
    var selectedSoil by remember { mutableStateOf("Loam (Ideal)") }
    var selectedStage by remember { mutableStateOf("Vegetative Stage") }

    var unitInBags by remember { mutableStateOf(false) } // False = Kg, True = 50kg Bags

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

        // Calculator Inputs Form with Slider/Stepper
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Farm Field Inputs",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                // Field Area Slider + Stepper (Farmer Friendly)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Field Area (Acres)", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { if (acresValue > 0.5f) acresValue = (acresValue - 0.5f).coerceAtLeast(0.5f) },
                                    modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.surface, CircleShape)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
                                }
                                Text(
                                    text = String.format("%.1f Acres", acresValue),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                IconButton(
                                    onClick = { if (acresValue < 25.0f) acresValue += 0.5f },
                                    modifier = Modifier.size(32.dp).background(MaterialTheme.colorScheme.surface, CircleShape)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
                                }
                            }
                        }

                        Slider(
                            value = acresValue,
                            onValueChange = { acresValue = Math.round(it * 2) / 2.0f },
                            valueRange = 0.5f..20.0f,
                            steps = 38,
                            modifier = Modifier.fillMaxWidth().testTag("area_acres_input")
                        )
                    }
                }

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
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
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
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
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
                        label = { Text("Crop Growth Stage") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = stageExpanded) },
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = stageExpanded,
                        onDismissRequest = { stageExpanded = false }
                    ) {
                        stageList.forEach { stage ->
                            DropdownMenuItem(
                                text = { Text(stage) },
                                onClick = {
                                    selectedStage = stage
                                    stageExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Calculate Button
                Button(
                    onClick = {
                        calcResult = viewModel.calculateFertilizer(
                            acres = acresValue.toDouble(),
                            cropType = selectedCrop,
                            soilType = selectedSoil,
                            stage = selectedStage
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_calculate_fertilizer"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Calculate, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("CALCULATE DOSAGE & WATER NEED", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }

        // Calculation Results Card with Unit Toggle
        calcResult?.let { res ->
            AnimatedVisibility(visible = true, enter = fadeIn()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🧪 Fertilizer & Water Recommendation",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )

                            // Unit Toggle Chip (Kg vs 50kg Bags)
                            SuggestionChip(
                                onClick = { unitInBags = !unitInBags },
                                label = {
                                    Text(
                                        if (unitInBags) "Unit: 50kg Bags" else "Unit: Kilograms (Kg)",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(containerColor = MaterialTheme.colorScheme.primary, labelColor = Color.White)
                            )
                        }

                        // Chemical Fertilizer Grid
                        Text(
                            text = "A. Recommended Chemical Fertilizers:",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        val ureaKg = res.nitrogenKg * 2.17
                        val dapKg = res.phosphorusKg * 2.17
                        val mopKg = res.potassiumKg * 1.66
                        val waterLiters = acresValue * 12000.0

                        val ureaDisplay = if (unitInBags) String.format("%.1f Bags", ureaKg / 50.0) else "${ureaKg.toInt()} Kg"
                        val dapDisplay = if (unitInBags) String.format("%.1f Bags", dapKg / 50.0) else "${dapKg.toInt()} Kg"
                        val mopDisplay = if (unitInBags) String.format("%.1f Bags", mopKg / 50.0) else "${mopKg.toInt()} Kg"

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FertilizerStatBox(label = "Urea (46% N)", value = ureaDisplay, modifier = Modifier.weight(1f))
                            FertilizerStatBox(label = "DAP (18-46-0)", value = dapDisplay, modifier = Modifier.weight(1f))
                            FertilizerStatBox(label = "MOP (60% K)", value = mopDisplay, modifier = Modifier.weight(1f))
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f))

                        // Organic Substitutes
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Eco, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "B. Organic Alternatives:",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Text(
                            text = "Vermicompost: ${res.vermicompostKg.toInt()} Kg • Neem Cake: ${res.neemCakeKg.toInt()} Kg • Panchagavya: ${res.panchagavyaLiters.toInt()} L",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f))

                        // Water Requirement
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WaterDrop, contentDescription = null, tint = Color(0xFF0284C7), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "C. Irrigation Water Needed:",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0369A1)
                            )
                        }

                        Text(
                            text = "${waterLiters.toInt()} Liters / week (${String.format("%.1f", waterLiters / 1000.0)} Kilo-Liters)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0369A1)
                        )

                        Text(
                            text = "💡 Application Schedule: ${res.stageSchedule.firstOrNull()?.second ?: "Apply in split doses."}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FertilizerStatBox(label: String, value: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.White.copy(alpha = 0.9f), shape = RoundedCornerShape(12.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
            Text(label, style = MaterialTheme.typography.labelSmall, fontSize = 9.5.sp, color = Color.Gray)
        }
    }
}
