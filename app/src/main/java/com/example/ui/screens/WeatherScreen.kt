package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.PestControl
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShieldMoon
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyForecast
import com.example.data.model.HourlyForecast
import com.example.data.model.HyperLocalWeatherData
import com.example.data.model.WeatherLocation
import com.example.ui.AgriViewModel
import com.example.ui.components.WeatherSummaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    val selectedLocation by viewModel.selectedWeatherLocation.collectAsState()
    val weatherData by viewModel.weatherData.collectAsState()
    val isLoading by viewModel.isWeatherLoading.collectAsState()
    val availableLocations = viewModel.availableWeatherLocations

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar: Location Tab Row
        ScrollableTabRow(
            selectedTabIndex = availableLocations.indexOf(selectedLocation).coerceAtLeast(0),
            edgePadding = 12.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            availableLocations.forEach { location ->
                val isSelected = location.name == selectedLocation.name
                Tab(
                    selected = isSelected,
                    onClick = { viewModel.selectWeatherLocation(location) },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = location.name,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium
                            )
                        }
                    },
                    modifier = Modifier.testTag("tab_weather_loc_${location.name.lowercase()}")
                )
            }
        }

        if (isLoading && weatherData == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Fetching live satellite weather & soil metrics...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        } else {
            val data = weatherData
            if (data != null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Weather Summary Card Component with Real-Time Climate & Agricultural Advice
                    item {
                        WeatherSummaryCard(
                            viewModel = viewModel
                        )
                    }
                    // Header Status Banner with Refresh Button
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${data.location.name}, ${data.location.state}",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (data.isLiveApi) Color(0xFFDCFCE7) else Color(0xFFFEF3C7),
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (data.isLiveApi) "LIVE SATELLITE" else "CACHED",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (data.isLiveApi) Color(0xFF166534) else Color(0xFF92400E)
                                        )
                                    }
                                }
                                Text(
                                    text = "Updated ${data.lastUpdatedText} • Primary crops: ${data.location.primaryCrops.joinToString(", ")}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }

                            IconButton(
                                onClick = { viewModel.refreshWeatherForSelectedLocation() },
                                modifier = Modifier.testTag("btn_refresh_weather")
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh Weather", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }

                    // Weather Hero Card
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${data.currentTempC.toInt()}°C",
                                                fontSize = 48.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "Feels like ${data.feelsLikeC.toInt()}°C",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                            )
                                            Text(
                                                text = data.conditionText,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (data.rainProbability > 50) Icons.Default.Umbrella else Icons.Default.WbSunny,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(44.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    // Metric Highlights Pill Row
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        WeatherPillMetric(
                                            icon = Icons.Default.Opacity,
                                            label = "Soil Moisture",
                                            value = "${data.soilMoisturePercent}%",
                                            tint = Color(0xFF0284C7)
                                        )
                                        WeatherPillMetric(
                                            icon = Icons.Default.WaterDrop,
                                            label = "Humidity",
                                            value = "${data.humidityPercent}%",
                                            tint = Color(0xFF0D9488)
                                        )
                                        WeatherPillMetric(
                                            icon = Icons.Default.Air,
                                            label = "Wind Speed",
                                            value = "${data.windSpeedKmh.toInt()} km/h ${data.windDirection}",
                                            tint = Color(0xFF6366F1)
                                        )
                                        WeatherPillMetric(
                                            icon = Icons.Default.Umbrella,
                                            label = "Rain Risk",
                                            value = "${data.rainProbability}%",
                                            tint = Color(0xFF0284C7)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Chemical / Organic Spraying Advisory
                    item {
                        val adv = data.advisory
                        val isSafe = adv.sprayStatus == "SAFE"
                        val isWarning = adv.sprayStatus == "WARNING"

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSafe) Color(0xFFF0FDF4) else if (isWarning) Color(0xFFFFFBEB) else Color(0xFFFEF2F2)
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSafe) Color(0xFF86EFAC) else if (isWarning) Color(0xFFFDE68A) else Color(0xFFFCA5A5)
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (isSafe) Icons.Default.CheckCircle else Icons.Default.Warning,
                                            contentDescription = null,
                                            tint = if (isSafe) Color(0xFF15803D) else if (isWarning) Color(0xFFB45309) else Color(0xFFB91C1C)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "SPRAYING CONDITIONS: ${adv.sprayStatus}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (isSafe) Color(0xFF14532D) else if (isWarning) Color(0xFF78350F) else Color(0xFF7F1D1D)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = adv.sprayCondition,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    // Precision Irrigation & Pest Risk Cards
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            AdvisoryDetailCard(
                                title = "💧 Precision Irrigation Advice",
                                description = data.advisory.irrigationAdvice,
                                icon = Icons.Default.WaterDrop,
                                iconTint = Color(0xFF0284C7)
                            )

                            AdvisoryDetailCard(
                                title = "🌾 Harvest & Threshing Window",
                                description = data.advisory.harvestAdvice,
                                icon = Icons.Default.Grass,
                                iconTint = Color(0xFF15803D)
                            )

                            AdvisoryDetailCard(
                                title = "🐛 Fungal & Pest Alert",
                                description = data.advisory.pestRiskAlert,
                                icon = Icons.Default.PestControl,
                                iconTint = Color(0xFFB45309)
                            )

                            AdvisoryDetailCard(
                                title = "☀️ Temperature & Heat Stress",
                                description = data.advisory.heatStressWarning,
                                icon = Icons.Default.Thermostat,
                                iconTint = Color(0xFFDC2626)
                            )
                        }
                    }

                    // Hourly Weather Forecast
                    item {
                        Column {
                            Text(
                                text = "Hourly Forecast (Next 6 Hours)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                items(data.hourlyForecast) { hourly ->
                                    HourlyWeatherChip(hourly)
                                }
                            }
                        }
                    }

                    // 5-Day Agricultural Forecast
                    item {
                        Column {
                            Text(
                                text = "5-Day Climate Outlook",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            data.dailyForecast.forEach { daily ->
                                DailyWeatherRow(daily)
                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherPillMetric(
    icon: ImageVector,
    label: String,
    value: String,
    tint: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun AdvisoryDetailCard(
    title: String,
    description: String,
    icon: ImageVector,
    iconTint: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(iconTint.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun HourlyWeatherChip(hourly: HourlyForecast) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = hourly.timeLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Icon(
                imageVector = if (hourly.rainProbPercent > 50) Icons.Default.Umbrella else Icons.Default.WbSunny,
                contentDescription = null,
                tint = if (hourly.rainProbPercent > 50) Color(0xFF0284C7) else Color(0xFFD97706),
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "${hourly.tempC}°C", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text(text = "${hourly.rainProbPercent}% rain", style = MaterialTheme.typography.labelSmall, fontSize = 9.sp, color = Color(0xFF0284C7))
        }
    }
}

@Composable
fun DailyWeatherRow(daily: DailyForecast) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = daily.dayLabel, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.width(110.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (daily.rainProbPercent > 50) Icons.Default.Umbrella else Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = if (daily.rainProbPercent > 50) Color(0xFF0284C7) else Color(0xFFD97706),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = daily.condition, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Text(
                text = "${daily.maxTempC}° / ${daily.minTempC}°C",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
