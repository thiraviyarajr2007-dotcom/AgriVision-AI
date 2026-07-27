package com.example.ui.screens

import android.widget.Toast
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AgriViewModel
import com.example.ui.theme.AgriSpacing

data class MandiPriceItem(
    val cropName: String,
    val marketLocation: String,
    val pricePerQuintal: Int,
    val priceChangeRs: Int,
    val isUp: Boolean,
    val arrivalVolume: String,
    val timestamp: String = "Updated 2 hrs ago"
)

data class GovtSchemeItem(
    val schemeName: String,
    val tagLine: String,
    val benefitAmount: String,
    val eligibilityTags: List<String>,
    val description: String
)

@Composable
fun MarketAndSchemesScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedMandiFilter by remember { mutableStateOf("All Markets") }

    val mandiLocations = listOf("All Markets", "Thanjavur", "Kolar", "Rajkot", "Guntur", "Davangere")

    val mandiPrices = listOf(
        MandiPriceItem("Paddy (Common)", "Thanjavur Mandi, TN", 2300, 45, true, "120 Tons", "Updated 1 hr ago"),
        MandiPriceItem("Tomato", "Kolar Mandi, KA", 2800, 120, true, "85 Tons", "Updated 30 mins ago"),
        MandiPriceItem("Cotton (Long Staple)", "Rajkot Mandi, GJ", 7250, 80, true, "210 Tons", "Updated 2 hrs ago"),
        MandiPriceItem("Chilli (Teja)", "Guntur Mandi, AP", 13500, -200, false, "150 Tons", "Updated 4 hrs ago"),
        MandiPriceItem("Maize", "Davangere Mandi, KA", 2150, 30, true, "95 Tons", "Updated 1 hr ago"),
        MandiPriceItem("Groundnut", "Junagadh Mandi, GJ", 6400, -50, false, "60 Tons", "Updated 3 hrs ago")
    )

    val filteredPrices = if (selectedMandiFilter == "All Markets") {
        mandiPrices
    } else {
        mandiPrices.filter { it.marketLocation.contains(selectedMandiFilter, ignoreCase = true) }
    }

    val govtSchemes = listOf(
        GovtSchemeItem(
            schemeName = "PM-KISAN Samman Nidhi",
            tagLine = "Direct Financial Support to All Landholding Farmer Families",
            benefitAmount = "₹6,000 / year (3 installments of ₹2,000)",
            eligibilityTags = listOf("Small Farmers", "Marginal Farmers", "Direct Bank Credit"),
            description = "Provides direct income support into bank accounts of farmers nationwide to purchase seeds, fertilizers, and farm inputs."
        ),
        GovtSchemeItem(
            schemeName = "PM Fasal Bima Yojana (PMFBY)",
            tagLine = "Comprehensive Crop Insurance Against Natural Calamities",
            benefitAmount = "Full Sum Insured Cover (Low Premium 1.5% - 2%)",
            eligibilityTags = listOf("All Farmers", "Notify Crops", "Drought / Flood"),
            description = "Protects crops against drought, floods, pest outbreaks, and post-harvest losses with minimal farmer premium."
        ),
        GovtSchemeItem(
            schemeName = "Kisan Credit Card (KCC)",
            tagLine = "Low-Interest Institutional Credit for Farming & Inputs",
            benefitAmount = "Collateral-free loan up to ₹1.6 Lakh @ 4% Interest",
            eligibilityTags = listOf("Tenant Farmers", "Sharecroppers", "SHGs", "4% Rate"),
            description = "Provides short-term credit facility for crop cultivation, post-harvest expenses, and maintenance of farm assets."
        ),
        GovtSchemeItem(
            schemeName = "PM Krishi Sinchayee Yojana (Micro-Irrigation)",
            tagLine = "Per Drop More Crop - Drip & Sprinkler Subsidy",
            benefitAmount = "Up to 80% Subsidy on Drip Irrigation Kits",
            eligibilityTags = listOf("Subsidy 80%", "Micro-Irrigation", "Drip Kit"),
            description = "Subsidizes precision micro-irrigation installations to conserve water and improve fertilizer efficiency."
        )
    )

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = AgriSpacing.sm)
                    ) {
                        Icon(Icons.Default.Storefront, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(AgriSpacing.xs))
                        Text("Live Mandi Prices", style = MaterialTheme.typography.titleSmall)
                    }
                },
                modifier = Modifier
                    .height(AgriSpacing.touchTargetMin)
                    .testTag("tab_mandi_prices")
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = AgriSpacing.sm)
                    ) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(AgriSpacing.xs))
                        Text("Government Schemes", style = MaterialTheme.typography.titleSmall)
                    }
                },
                modifier = Modifier
                    .height(AgriSpacing.touchTargetMin)
                    .testTag("tab_govt_schemes")
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            when (selectedTab) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("APMC Mandi Live Wholesale Rates", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Text("Real-time crop arrivals & daily price trend arrows", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }

                        // Mandi Location Filter Chips Row
                        item {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(mandiLocations) { loc ->
                                    val isSelected = selectedMandiFilter == loc
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .border(
                                                1.dp,
                                                if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f),
                                                shape = RoundedCornerShape(20.dp)
                                            )
                                            .clickable { selectedMandiFilter = loc }
                                            .padding(horizontal = 14.dp, vertical = 6.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = if (isSelected) Color.White else Color.Gray, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                loc,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        items(filteredPrices) { item ->
                            MandiPriceCard(item = item)
                        }
                    }
                }

                1 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(govtSchemes) { scheme ->
                            GovtSchemeCard(scheme = scheme, onApply = {
                                Toast.makeText(context, "Opening application flow for ${scheme.schemeName}", Toast.LENGTH_SHORT).show()
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MandiPriceCard(item: MandiPriceItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(item.cropName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(item.marketLocation, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("₹${item.pricePerQuintal} / Q", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (item.isUp) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = if (item.isUp) Color(0xFF15803D) else Color(0xFFDC2626),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${if (item.isUp) "+" else ""}${item.priceChangeRs}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (item.isUp) Color(0xFF15803D) else Color(0xFFDC2626)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Arrivals: ${item.arrivalVolume}", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(11.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(item.timestamp, style = MaterialTheme.typography.labelSmall, fontSize = 10.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun GovtSchemeCard(scheme: GovtSchemeItem, onApply: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AccountBalance, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(scheme.schemeName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(scheme.tagLine, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "💰 Benefit: ${scheme.benefitAmount}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Text(scheme.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            // Eligibility Chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(scheme.eligibilityTags) { tag ->
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFDCFCE7), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "✓ $tag",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534)
                        )
                    }
                }
            }

            // Apply Now CTA
            Button(
                onClick = onApply,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("APPLY NOW / CHECK ELIGIBILITY ->", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }
    }
}
