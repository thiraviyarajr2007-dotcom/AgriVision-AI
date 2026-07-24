package com.example.ui.screens

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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    val regionBadge: String
)

val availableLanguages = listOf(
    LanguageOption("English", "English", "English", "Precision Farming & AI Crop Doctor", "Default"),
    LanguageOption("Tamil", "தமிழ்", "Tamil", "துல்லிய விவசாயம் & பயிர் மருத்துவர்", "தமிழ்நாடு"),
    LanguageOption("Hindi", "हिंदी", "Hindi", "सटीक कृषि और फसल डॉक्टर", "भारत"),
    LanguageOption("Telugu", "తెలుగు", "Telugu", "ఖచ్చితమైన వ్యవసాయం & పంట డాక్టర్", "ఆంధ్ర / తెలంగాణ"),
    LanguageOption("Malayalam", "മലയാളം", "Malayalam", "കൃത്യതാ കൃഷിയും വിള ഡോക്ടറും", "കേരളം"),
    LanguageOption("Marathi", "मराठी", "Marathi", "अचूक शेती आणि पीक डॉक्टर", "महाराष्ट्र"),
    LanguageOption("Kannada", "ಕನ್ನಡ", "Kannada", "ನಿಖರ ಕೃಷಿ ಮತ್ತು ಬೆಳೆ ವೈದ್ಯ", "ಕರ್ನಾಟಕ"),
    LanguageOption("Gujarati", "ગુજરાતી", "Gujarati", "ચોક્કસ ખેતી અને પાક ડોક્ટર", "ગુજરાત"),
    LanguageOption("Punjabi", "ਪੰਜਾਬੀ", "Punjabi", "ਸਟੀਕ ਖੇਤੀਬਾੜੀ ਅਤੇ ਫਸਲ ਡਾਕਟਰ", "ਪੰਜਾਬ"),
    LanguageOption("Bengali", "বাংলা", "Bengali", "সঠিক কৃষি ও ফসল চিকিৎসক", "পশ্চিমবঙ্গ")
)

@Composable
fun LanguageSelectionScreen(
    viewModel: AgriViewModel,
    onConfirmLanguage: () -> Unit,
    modifier: Modifier = Modifier
) {
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

            // Language Cards Grid
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

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(118.dp)
                            .clickable {
                                selectedLangCode = option.code
                                viewModel.selectLanguage(option.code)
                            }
                            .testTag("lang_option_${option.code.lowercase()}"),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected)
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f)
                            else
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f)
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = option.nativeName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )

                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = option.englishName,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Text(
                                    text = option.subtext,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Button
            Button(
                onClick = {
                    viewModel.completeLanguageSetup(selectedLangCode)
                    onConfirmLanguage()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("btn_confirm_language"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
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
                        text = "Continue with ${selectedLangCode} ->",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
