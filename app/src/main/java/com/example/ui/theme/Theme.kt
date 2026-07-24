package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = Color(0xFFA7C97C),
    onPrimary = Color(0xFF142300),
    primaryContainer = Color(0xFF2A5001),
    onPrimaryContainer = Color(0xFFD2EEA6),
    secondary = Color(0xFFD2EEA6),
    onSecondary = Color(0xFF142300),
    secondaryContainer = Color(0xFF386B01),
    onSecondaryContainer = Color(0xFFE9EFD1),
    tertiary = Color(0xFFFFB4AB),
    tertiaryContainer = Color(0xFF93000A),
    onTertiaryContainer = Color(0xFFFFDAD6),
    background = AgriDarkBackground,
    surface = AgriDarkSurface,
    surfaceVariant = AgriDarkSurfaceVariant,
    outline = AgriOutline
  )

private val LightColorScheme =
  lightColorScheme(
    primary = AgriGreenPrimary,
    onPrimary = AgriGreenOnPrimary,
    primaryContainer = AgriGreenContainer,
    onPrimaryContainer = AgriGreenOnContainer,
    secondary = AgriSecondaryDarkGreen,
    onSecondary = Color.White,
    secondaryContainer = AgriSecondaryContainer,
    onSecondaryContainer = AgriOnSecondaryContainer,
    tertiary = AgriAlertTertiary,
    tertiaryContainer = AgriAlertContainer,
    onTertiaryContainer = AgriOnAlertContainer,
    background = AgriLightBackground,
    surface = AgriLightSurface,
    surfaceVariant = AgriLightSurfaceVariant,
    outline = AgriOutline
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disable dynamic color by default so the custom Vibrant Palette aesthetic is strictly applied
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
