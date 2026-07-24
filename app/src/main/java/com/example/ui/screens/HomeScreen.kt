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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.sp
import com.example.ui.AgriViewModel
import com.example.util.Localization

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: AgriViewModel) {
    val showSplash by viewModel.showSplashScreen.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    if (showSplash) {
        SplashScreen(onContinue = { viewModel.dismissSplash() })
        return
    }

    if (!isLoggedIn) {
        LoginScreen(viewModel = viewModel)
        return
    }

    var selectedTab by remember { mutableIntStateOf(0) }
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
                                    contentDescription = null,
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // Language Selector Header Strip
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 4.dp)) {
                            Icon(Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Lang:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }

                    items(languages) { (code, displayName) ->
                        val isSelected = currentLanguage.equals(code, true) || currentLanguage.equals(displayName, true)
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable { viewModel.selectLanguage(code) }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = displayName,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text(Localization.getString("nav_home", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_home"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.QrCodeScanner, contentDescription = "Scan") },
                    label = { Text(Localization.getString("nav_scan", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_scan"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Landscape, contentDescription = "Crops") },
                    label = { Text(Localization.getString("nav_crops", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_crops"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Icon(Icons.Default.ShowChart, contentDescription = "ML Yield") },
                    label = { Text(Localization.getString("nav_yield", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_yield"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    icon = { Icon(Icons.Default.Forum, contentDescription = "Forum") },
                    label = { Text(Localization.getString("nav_forum", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_forum"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                NavigationBarItem(
                    selected = selectedTab == 5,
                    onClick = { selectedTab = 5 },
                    icon = { Icon(Icons.Default.Psychology, contentDescription = "AI Chat") },
                    label = { Text(Localization.getString("nav_chat", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_chat"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                NavigationBarItem(
                    selected = selectedTab == 6,
                    onClick = { selectedTab = 6 },
                    icon = { Icon(Icons.Default.Storefront, contentDescription = "Market") },
                    label = { Text(Localization.getString("nav_market", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_market"),
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = MaterialTheme.colorScheme.primary, indicatorColor = MaterialTheme.colorScheme.primaryContainer)
                )

                NavigationBarItem(
                    selected = selectedTab == 7,
                    onClick = { selectedTab = 7 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text(Localization.getString("nav_profile", currentLanguage), fontSize = 10.sp) },
                    modifier = Modifier.testTag("nav_tab_profile"),
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
            }
        }
    }
}
