package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AgriViewModel
import com.example.util.Localization

import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.SignalCellularConnectedNoInternet4Bar
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.IconButton
import com.example.ui.components.MoreDestinationsSheet
import com.example.ui.components.OfflineSyncIndicator
import com.example.ui.components.VoiceAssistanceModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: AgriViewModel) {
    val showSplash by viewModel.showSplashScreen.collectAsState()
    val hasCompletedOnboarding by viewModel.hasCompletedOnboarding.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val hasCompletedLanguageSetup by viewModel.hasCompletedLanguageSetup.collectAsState()
    val isVoiceModalOpen by viewModel.isVoiceModalOpen.collectAsState()
    val isOfflineMode by viewModel.isOfflineMode.collectAsState()

    if (showSplash) {
        SplashScreen(onContinue = { viewModel.dismissSplash() })
        return
    }

    if (!hasCompletedOnboarding) {
        OnboardingScreen(onFinishOnboarding = { viewModel.completeOnboarding() })
        return
    }

    if (!hasCompletedLanguageSetup) {
        LanguageSelectionScreen(
            viewModel = viewModel,
            onConfirmLanguage = {
                // Handled in viewModel
            }
        )
        return
    }

    if (!isLoggedIn) {
        LoginScreen(viewModel = viewModel)
        return
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.detectAndApplyDeviceLocation(context)
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    var showMoreSheet by remember { mutableStateOf(false) }
    val currentLanguage by viewModel.selectedLanguage.collectAsState()

    val languages = listOf(
        "English" to "English",
        "Tamil" to "தமிழ்",
        "Hindi" to "हिंदी",
        "Telugu" to "తెలుగు",
        "Malayalam" to "മലയാളം",
        "Marathi" to "मराठी",
        "Kannada" to "ಕನ್ನಡ",
        "Gujarati" to "ગુજરાતી",
        "Punjabi" to "ਪੰਜਾਬੀ",
        "Bengali" to "বাংলা"
    )

    if (showMoreSheet) {
        MoreDestinationsSheet(
            onDismiss = { showMoreSheet = false },
            onSelectDestination = { index ->
                selectedTab = index
            },
            currentLanguage = currentLanguage
        )
    }

    if (isVoiceModalOpen) {
        VoiceAssistanceModal(
            viewModel = viewModel,
            onNavigateTab = { index -> selectedTab = index },
            onDismiss = { viewModel.closeVoiceAssistant() }
        )
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = "AgriCare App Logo",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "AgriVision AI",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = Localization.getString("app_subtitle", currentLanguage),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }
                        }
                    },
                    actions = {
                        // Voice Assistant Mic Button
                        IconButton(
                            onClick = { viewModel.openVoiceAssistant() },
                            modifier = Modifier.testTag("btn_voice_assistant")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "Voice Assistant Mic",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Offline Simulation Toggle
                        IconButton(
                            onClick = { viewModel.toggleOfflineSimulation(!isOfflineMode) },
                            modifier = Modifier.testTag("btn_toggle_offline")
                        ) {
                            Icon(
                                imageVector = if (isOfflineMode) Icons.Default.SignalCellularConnectedNoInternet4Bar else Icons.Default.Wifi,
                                contentDescription = "Toggle Network Status",
                                tint = if (isOfflineMode) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // Persistent Offline / Sync Indicator
                OfflineSyncIndicator(isOffline = isOfflineMode, currentLanguage = currentLanguage)
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                // Tab 0: Home
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home Tab") },
                    label = { Text(Localization.getString("nav_home", currentLanguage), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("nav_tab_home"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                // Tab 1: Scan / Diagnosis
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan Crop Leaf Tab") },
                    label = { Text(Localization.getString("nav_scan", currentLanguage), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("nav_tab_scan"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                // Tab 5: AI Chat
                NavigationBarItem(
                    selected = selectedTab == 5,
                    onClick = { selectedTab = 5 },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "Kisan AI Assistant Chat") },
                    label = { Text(Localization.getString("nav_chat", currentLanguage), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("nav_tab_chat"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                // Tab 6: Market & Schemes
                NavigationBarItem(
                    selected = selectedTab == 6,
                    onClick = { selectedTab = 6 },
                    icon = { Icon(Icons.Default.Storefront, contentDescription = "Mandi Prices and Govt Schemes Tab") },
                    label = { Text(Localization.getString("nav_market", currentLanguage), fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("nav_tab_market"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                // Tab 8: More (Fields, Yield, Forum, Calculators, Profile)
                val isMoreActive = selectedTab !in listOf(0, 1, 5, 6)
                NavigationBarItem(
                    selected = isMoreActive,
                    onClick = { showMoreSheet = true },
                    icon = { Icon(Icons.Default.Apps, contentDescription = "More Agri Tools") },
                    label = { Text("More", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("nav_tab_more"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeDashboardScreen(viewModel = viewModel, onNavigateTab = { selectedTab = it })
                1 -> DiagnosisScreen(viewModel = viewModel)
                2 -> MyFieldsScreen(viewModel = viewModel)
                3 -> YieldAndSoilScreen(viewModel = viewModel)
                4 -> CommunityForumScreen(viewModel = viewModel)
                5 -> AgriChatScreen(viewModel = viewModel)
                6 -> MarketAndSchemesScreen(viewModel = viewModel)
                7 -> UserProfileScreen(viewModel = viewModel)
                8 -> WeatherScreen(viewModel = viewModel)
                9 -> CalculatorsScreen(viewModel = viewModel)
                else -> HomeDashboardScreen(viewModel = viewModel, onNavigateTab = { selectedTab = it })
            }
        }
    }
}
