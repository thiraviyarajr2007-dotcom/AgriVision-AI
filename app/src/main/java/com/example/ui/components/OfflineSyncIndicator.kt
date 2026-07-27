package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AgriSpacing

@Composable
fun OfflineSyncIndicator(
    isOffline: Boolean = false,
    currentLanguage: String = "English",
    modifier: Modifier = Modifier
) {
    val regionName = when {
        currentLanguage.contains("Tamil", ignoreCase = true) || currentLanguage.equals("ta", ignoreCase = true) -> "Tamil Nadu Farmers"
        currentLanguage.contains("Kannada", ignoreCase = true) || currentLanguage.contains("Kanada", ignoreCase = true) || currentLanguage.equals("kn", ignoreCase = true) -> "Karnataka Farmers"
        currentLanguage.contains("Hindi", ignoreCase = true) || currentLanguage.equals("hi", ignoreCase = true) -> "North India Farmers"
        currentLanguage.contains("Telugu", ignoreCase = true) || currentLanguage.equals("te", ignoreCase = true) -> "Andhra & Telangana Farmers"
        currentLanguage.contains("Malayalam", ignoreCase = true) || currentLanguage.equals("ml", ignoreCase = true) -> "Kerala Farmers"
        currentLanguage.contains("Marathi", ignoreCase = true) || currentLanguage.equals("mr", ignoreCase = true) -> "Maharashtra Farmers"
        currentLanguage.contains("Gujarati", ignoreCase = true) || currentLanguage.equals("gu", ignoreCase = true) -> "Gujarat Farmers"
        currentLanguage.contains("Punjabi", ignoreCase = true) || currentLanguage.equals("pa", ignoreCase = true) -> "Punjab Farmers"
        currentLanguage.contains("Bengali", ignoreCase = true) || currentLanguage.equals("bn", ignoreCase = true) -> "West Bengal Farmers"
        else -> "Tamil Nadu Farmers"
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    if (isOffline) MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.9f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.85f)
                )
                .padding(horizontal = AgriSpacing.md, vertical = AgriSpacing.xs)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            if (isOffline) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(AgriSpacing.sm))
                Icon(
                    imageVector = if (isOffline) Icons.Default.CloudOff else Icons.Default.CloudDone,
                    contentDescription = null,
                    tint = if (isOffline) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(AgriSpacing.xs))
                Text(
                    text = if (isOffline) "Offline Mode • Showing Local Cache" else "Cloud Sync Active • $regionName",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = if (isOffline) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
