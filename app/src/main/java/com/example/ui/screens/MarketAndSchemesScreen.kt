package com.example.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.AgriViewModel

data class MandiPriceItem(
    val cropName: String,
    val marketLocation: String,
    val pricePerQuintal: Int,
    val priceChangeRs: Int,
    val isUp: Boolean,
    val arrivalVolume: String
)

data class GovtSchemeItem(
    val schemeName: String,
    val tagLine: String,
    val benefitAmount: String,
    val eligibility: String,
    val description: String
)

@Composable
fun MarketAndSchemesScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val mandiPrices = listOf(
        MandiPriceItem("Paddy (Common)", "Thanjavur Mandi, TN", 2300, 45, true, "120 Tons"),
        MandiPriceItem("Tomato", "Kolar Mandi, KA", 2800, 120, true, "85 Tons"),
        MandiPriceItem("Cotton (Long Staple)", "Rajkot Mandi, GJ", 7250, 80, true, "210 Tons"),
        MandiPriceItem("Chilli (Teja)", "Guntur Mandi, AP", 13500, -200, false, "150 Tons"),
        MandiPriceItem("Maize", "Davangere Mandi, KA", 2150, 30, true, "95 Tons"),
        MandiPriceItem("Groundnut", "Junagadh Mandi, GJ", 6400, -50, false, "60 Tons")
    )

    val govtSchemes = listOf(
        GovtSchemeItem(
            schemeName = "PM-KISAN Samman Nidhi",
            tagLine = "Direct Financial Support to All Landholding Farmer Families",
            benefitAmount = "₹6,000 / year (3 installments of ₹2,000)",
            eligibility = "All small & marginal farmers with cultivable land holding",
            description = "Provides direct income support into bank accounts of farmers nationwide to purchase seeds, fertilizers, and farm inputs."
        ),
        GovtSchemeItem(
            schemeName = "PM Fasal Bima Yojana (PMFBY)",
            tagLine = "Comprehensive Crop Insurance Against Natural Calamities",
            benefitAmount = "Full Sum Insured Cover (Low Premium 1.5% - 2%)",
            eligibility = "All farmers growing notified crops in notified areas",
            description = "Protects crops against drought, floods, pest outbreaks, and post-harvest losses with minimal farmer premium."
        ),
        GovtSchemeItem(
            schemeName = "Kisan Credit Card (KCC)",
            tagLine = "Low-Interest Institutional Credit for Farming & Inputs",
            benefitAmount = "Collateral-free loan up to ₹1.6 Lakh @ 4% Interest",
            eligibility = "Farmers, Tenant Farmers, Sharecroppers & SHGs",
            description = "Provides short-term credit facility for crop cultivation, post-harvest expenses, and maintenance of farm assets."
        ),
        GovtSchemeItem(
            schemeName = "PM Krishi Sinchayee Yojana (Micro-Irrigation)",
            tagLine = "Per Drop More Crop - Drip & Sprinkler Subsidy",
            benefitAmount = "Up to 80% Subsidy on Drip Irrigation Kits",
            eligibility = "All farmers with agricultural land and water source",
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Storefront, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Live Mandi Prices")
                    }
                },
                modifier = Modifier.testTag("tab_mandi_prices")
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Government Schemes")
                    }
                },
                modifier = Modifier.testTag("tab_govt_schemes")
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
                                        Text("APMC Mandi Live Rates", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                        Text("Daily market arrivals & wholesale price trends", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }

                        items(mandiPrices) { item ->
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
                            GovtSchemeCard(scheme = scheme)
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(item.cropName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(item.marketLocation, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Daily Arrival: ${item.arrivalVolume}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("₹${item.pricePerQuintal}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (item.isUp) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = null,
                        tint = if (item.isUp) Color(0xFF2E7D32) else Color(0xFFC62828),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${if (item.isUp) "+" else ""}${item.priceChangeRs}/Q",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (item.isUp) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }
        }
    }
}

@Composable
fun GovtSchemeCard(scheme: GovtSchemeItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AccountBalance, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(scheme.schemeName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(scheme.tagLine, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Text(
                    text = "💰 Benefit: ${scheme.benefitAmount}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(scheme.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(6.dp))

            Text("Eligible: ${scheme.eligibility}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}
