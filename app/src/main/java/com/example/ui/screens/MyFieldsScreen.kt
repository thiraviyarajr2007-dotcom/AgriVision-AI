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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.data.local.CropProfileEntity
import com.example.data.local.DiagnosisEntity
import com.example.ui.AgriViewModel
import com.example.ui.components.AgriEmptyState
import com.example.ui.theme.AgriSpacing
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyFieldsScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    val histories by viewModel.allDiagnoses.collectAsState()
    val profiles by viewModel.allCropProfiles.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var showAddDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Tab Header
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Landscape, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Active Fields (${profiles.size})")
                        }
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Scan History (${histories.size})")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTabIndex) {
                0 -> ActiveFieldsList(profiles = profiles, onDelete = { viewModel.deleteCropProfile(it) })
                1 -> ScanHistoryList(histories = histories, onDelete = { viewModel.deleteDiagnosisHistory(it) })
            }
        }

        // Floating Action Button to add field profile
        if (selectedTabIndex == 0) {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .testTag("add_field_fab"),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Crop Field", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }

    if (showAddDialog) {
        AddFieldDialog(
            onDismiss = { showAddDialog = false },
            onSave = { fieldName, cropType, soilType, acres, notes ->
                viewModel.addCropProfile(fieldName, cropType, soilType, acres, notes)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun ActiveFieldsList(
    profiles: List<CropProfileEntity>,
    onDelete: (Long) -> Unit
) {
    if (profiles.isEmpty()) {
        AgriEmptyState(
            icon = Icons.Default.Agriculture,
            title = "No Active Crop Fields Saved",
            description = "Register your paddy, tomato, or sugarcane plot to track real-time growth stages, soil health, and advisory schedules.",
            actionLabel = "Add First Field Plot",
            onActionClick = { /* Handled by FAB or open dialog */ }
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(AgriSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(AgriSpacing.md)
        ) {
            items(profiles) { profile ->
                val daysOld = remember(profile.sowingDate) {
                    val diff = System.currentTimeMillis() - profile.sowingDate
                    val days = (diff / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(12)
                    days
                }
                val growthPercent = (daysOld * 100 / 110).coerceIn(15, 95)
                val growthStage = when {
                    growthPercent < 25 -> "Seedling"
                    growthPercent < 50 -> "Vegetative"
                    growthPercent < 75 -> "Flowering"
                    growthPercent < 90 -> "Fruiting"
                    else -> "Harvest Ready"
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AgriSpacing.cardRadius),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(AgriSpacing.lg)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Landscape, contentDescription = "Crop Field Icon", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                                }
                                Spacer(modifier = Modifier.width(AgriSpacing.md))
                                Column {
                                    Text(profile.fieldName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                    Text("${profile.cropType} • ${profile.fieldAreaAcres} Acres • Soil: ${profile.soilType}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                                }
                            }

                            IconButton(
                                onClick = { onDelete(profile.id) },
                                modifier = Modifier.size(AgriSpacing.touchTargetMin)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete Field", tint = Color.Gray)
                            }
                        }

                        Spacer(modifier = Modifier.height(AgriSpacing.md))

                        // Growth Monitoring Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Growth Stage: $growthStage", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                            Text("Age: $daysOld Days ($growthPercent%)", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }

                        Spacer(modifier = Modifier.height(AgriSpacing.xs))

                        // Growth Progress Bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(4.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(growthPercent / 100f)
                                    .height(8.dp)
                                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(4.dp))
                            )
                        }

                        // Growth Stage Indicators
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = AgriSpacing.xs),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Seedling", fontSize = 10.sp, color = Color.Gray)
                            Text("Vegetative", fontSize = 10.sp, color = Color.Gray)
                            Text("Flowering", fontSize = 10.sp, color = Color.Gray)
                            Text("Fruiting", fontSize = 10.sp, color = Color.Gray)
                            Text("Harvest", fontSize = 10.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(AgriSpacing.md))

                        // Dashboard Status Badges Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = AgriSpacing.sm, vertical = AgriSpacing.xs)
                            ) {
                                Text("Health: ${profile.healthScore}%", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }

                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = AgriSpacing.sm, vertical = AgriSpacing.xs)
                            ) {
                                Text("Water: Optimal", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }

                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                                    .padding(horizontal = AgriSpacing.sm, vertical = AgriSpacing.xs)
                            ) {
                                Text("NPK: On Schedule", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        if (profile.notes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(AgriSpacing.sm))
                            Text("Notes: ${profile.notes}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScanHistoryList(
    histories: List<DiagnosisEntity>,
    onDelete: (Long) -> Unit
) {
    if (histories.isEmpty()) {
        AgriEmptyState(
            icon = Icons.Default.History,
            title = "No Past Leaf Scans Stored",
            description = "Whenever you analyze a crop leaf for diseases or pest symptoms, your diagnostic reports will be preserved here offline."
        )
    } else {
        val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(AgriSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(AgriSpacing.md)
        ) {
            items(histories) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(AgriSpacing.cardRadius),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(AgriSpacing.lg)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.cropName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text(item.diseaseName, style = MaterialTheme.typography.bodyMedium, color = if (item.isHealthy) Color(0xFF15803D) else Color(0xFFC62828), fontWeight = FontWeight.SemiBold)
                                Text(dateFormat.format(Date(item.timestamp)), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                            IconButton(
                                onClick = { onDelete(item.id) },
                                modifier = Modifier.size(AgriSpacing.touchTargetMin)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete Scan Record", tint = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddFieldDialog(
    onDismiss: () -> Unit,
    onSave: (fieldName: String, cropType: String, soilType: String, acres: Double, notes: String) -> Unit
) {
    var fieldName by remember { mutableStateOf("") }
    var cropType by remember { mutableStateOf("Paddy (Rice)") }
    var soilType by remember { mutableStateOf("Loam") }
    var acresText by remember { mutableStateOf("1.0") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Crop Field") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = fieldName,
                    onValueChange = { fieldName = it },
                    label = { Text("Field Name (e.g., North Plot)") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = cropType,
                    onValueChange = { cropType = it },
                    label = { Text("Crop Variety / Species") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = soilType,
                    onValueChange = { soilType = it },
                    label = { Text("Soil Type") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = acresText,
                    onValueChange = { acresText = it },
                    label = { Text("Area (Acres)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Optional)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (fieldName.isNotBlank()) {
                        val acres = acresText.toDoubleOrNull() ?: 1.0
                        onSave(fieldName, cropType, soilType, acres, notes)
                    }
                }
            ) {
                Text("Save Field")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
