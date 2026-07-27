package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AgriSpacing

import androidx.compose.material.icons.filled.WbSunny

data class MoreDestinationItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val targetIndex: Int,
    val tag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreDestinationsSheet(
    onDismiss: () -> Unit,
    onSelectDestination: (Int) -> Unit,
    currentLanguage: String
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val destinations = listOf(
        MoreDestinationItem("Weather & Climate", "Satellite & soil advisory", Icons.Default.WbSunny, 8, "more_item_weather"),
        MoreDestinationItem("My Fields", "Manage crop plots & history", Icons.Default.Landscape, 2, "more_item_fields"),
        MoreDestinationItem("Yield & Soil ML", "Predict harvest & soil check", Icons.Default.ShowChart, 3, "more_item_yield"),
        MoreDestinationItem("Kisan Forum", "Farmer community discussions", Icons.Default.Forum, 4, "more_item_forum"),
        MoreDestinationItem("Agri Calculators", "NPK & seed rate tools", Icons.Default.Calculate, 9, "more_item_calc"),
        MoreDestinationItem("User Profile", "Farm info & account settings", Icons.Default.Person, 7, "more_item_profile")
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = AgriSpacing.xl, topEnd = AgriSpacing.xl)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AgriSpacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "More Agriculture Tools",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Quick access to all AgriCare features",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.defaultMinSize(minWidth = AgriSpacing.touchTargetMin, minHeight = AgriSpacing.touchTargetMin)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close More Sheet")
                }
            }

            Spacer(modifier = Modifier.height(AgriSpacing.lg))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(AgriSpacing.md),
                verticalArrangement = Arrangement.spacedBy(AgriSpacing.md),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(destinations) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = AgriSpacing.touchTargetMin)
                            .clickable {
                                onSelectDestination(item.targetIndex)
                                onDismiss()
                            }
                            .testTag(item.tag),
                        shape = RoundedCornerShape(AgriSpacing.cardRadius),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(AgriSpacing.lg),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(AgriSpacing.md))

                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = item.subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(AgriSpacing.xl))
        }
    }
}
