package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AgriViewModel

data class LanguageOption(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val subtext: String,
    val regionBadge: String,
    val accentColor: Color
)

val availableLanguages = listOf(
    LanguageOption("English", "English", "English", "Precision Farming & AI Crop Doctor", "Default", Color(0xFF1E88E5)),
    LanguageOption("Tamil", "தமிழ்", "Tamil", "துல்லிய விவசாயம் & பயிர் மருத்துவர்", "தமிழ்நாடு", Color(0xFFD81B60)),
    LanguageOption("Hindi", "हिंदी", "Hindi", "सटीक कृषि और फसल डॉक्टर", "भारत", Color(0xFFFB8C00)),
    LanguageOption("Telugu", "తెలుగు", "Telugu", "ఖచ్చితమైన వ్యవసాయం & పంట డాక్టర్", "ఆంధ్ర / తెలంగాణ", Color(0xFF43A047)),
    LanguageOption("Malayalam", "മലയാളം", "Malayalam", "കൃത്യതാ കൃഷിയും വിള ഡോക്ടറും", "കേരളം", Color(0xFF00ACC1)),
    LanguageOption("Marathi", "मराठी", "Marathi", "अचूक शेती आणि पीक डॉक्टर", "महाराष्ट्र", Color(0xFF8E24AA)),
    LanguageOption("Kannada", "ಕನ್ನಡ", "Kannada", "ನಿಖರ ಕೃಷಿ ಮತ್ತು ಬೆಳೆ ವೈದ್ಯ", "ಕರ್ನಾಟಕ", Color(0xFFFDD835)),
    LanguageOption("Gujarati", "ગુજરાતી", "Gujarati", "ચોક્કસ ખેતી અને પાક ડોક્ટર", "ગુજરાત", Color(0xFFE64A19)),
    LanguageOption("Punjabi", "ਪੰਜਾਬੀ", "Punjabi", "ਸਟੀਕ ਖੇਤੀਬਾੜੀ ਅਤੇ ਫਸਲ ਡਾਕਟਰ", "ਪੰਜਾਬ", Color(0xFF3949AB)),
    LanguageOption("Bengali", "বাংলা", "Bengali", "সঠিক কৃষি ও ফসল চিকিৎসক", "পশ্চিমবঙ্গ", Color(0xFF00897B))
)

@Composable
fun LanguageSelectionScreen(
    viewModel: AgriViewModel,
    onConfirmLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedLangCode by remember { mutableStateOf("English") }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .testTag("language_selection_screen"),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Header Icon & Title
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                Color(0xFF1B4300)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Select Your Preferred Language",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = "உங்கள் மொழியைத் தேர்ந்தெடுக்கவும் • भाषा चुनें",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Language Cards Grid - Native Script prominent
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(availableLanguages) { option ->
                    val isSelected = selectedLangCode.equals(option.code, ignoreCase = true)

                    val animatedBorderColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f),
                        animationSpec = tween(durationMillis = 300),
                        label = "borderColor"
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(132.dp)
                            .clickable {
                                selectedLangCode = option.code
                                viewModel.selectLanguage(option.code)
                            }
                            .testTag("lang_option_${option.code.lowercase()}"),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected)
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
                            else
                                MaterialTheme.colorScheme.surface
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isSelected) 2.5.dp else 1.dp,
                            color = animatedBorderColor
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = if (isSelected) 4.dp else 1.dp
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    // Regional Accent Tag
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                option.accentColor.copy(alpha = 0.15f),
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Default.Place,
                                                contentDescription = null,
                                                tint = option.accentColor,
                                                modifier = Modifier.size(10.dp)
                                            )
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(
                                                text = option.regionBadge,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = option.accentColor
                                            )
                                        }
                                    }

                                    // Checkmark
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                // Native script as BIGGEST text
                                Text(
                                    text = option.nativeName,
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 20.sp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = option.englishName,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = option.subtext,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 9.5.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    viewModel.detectAndApplyDeviceLocation(context)
                    val loc = viewModel.autoDetectedLocation.value
                    if (loc != null) {
                        selectedLangCode = loc.detectedLanguage
                        viewModel.completeLanguageSetup(loc.detectedLanguage)
                        onConfirmLanguage()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("btn_auto_detect_lang_screen"),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.MyLocation, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "📍 Auto-Detect State & Dialect (Tamil Nadu / Karnataka)",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Persistent Continue Button (Disabled until language is selected)
            Button(
                onClick = {
                    if (selectedLangCode.isNotBlank()) {
                        viewModel.completeLanguageSetup(selectedLangCode)
                        onConfirmLanguage()
                    }
                },
                enabled = selectedLangCode.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("btn_confirm_language"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.LightGray.copy(alpha = 0.6f)
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (selectedLangCode.isNotBlank()) "Continue with $selectedLangCode ->" else "Select a Language to Continue",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
