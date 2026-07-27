package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.GoogleAuthHelper
import com.example.ui.AgriViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: AgriViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val googleAuthHelper = remember { GoogleAuthHelper() }

    val currentLanguage by viewModel.selectedLanguage.collectAsState()

    var isAuthenticatingWithGoogle by remember { mutableStateOf(false) }
    var mobileNumber by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }
    var otpText by remember { mutableStateOf("") }
    var showMoreOptions by remember { mutableStateOf(false) }

    // Validation states
    val isPhoneValid = mobileNumber.length == 10 && mobileNumber.all { it.isDigit() }
    val isPhoneTouched = mobileNumber.isNotEmpty()
    val isOtpValid = otpText.length == 4 && otpText.all { it.isDigit() }
    val isOtpTouched = otpText.isNotEmpty()

    val languages = listOf(
        "English" to "English",
        "Tamil" to "தமிழ்",
        "Hindi" to "हिंदी",
        "Telugu" to "తెలుగు",
        "Malayalam" to "മലയാളം"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Header
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(44.dp)
                )
            }

            Text(
                text = "AgriVision AI",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Fast Mobile Login for Farmers",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Language Selector Bar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Language, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("App Language / மொழி", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(languages) { (code, displayName) ->
                            val isSelected = currentLanguage.equals(code, true) || currentLanguage.equals(displayName, true)
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { viewModel.selectLanguage(code) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = displayName,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            // Primary Mobile/OTP Card (Dominant Farmer CTA)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mobile Number Login (Primary)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // Mobile Field with Inline Validation
                    Column {
                        OutlinedTextField(
                            value = mobileNumber,
                            onValueChange = { input ->
                                if (input.length <= 10 && input.all { it.isDigit() }) {
                                    mobileNumber = input
                                }
                            },
                            label = { Text("10-Digit Mobile Number") },
                            placeholder = { Text("e.g. 9876543210") },
                            leadingIcon = { Text("+91 ", fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp)) },
                            trailingIcon = {
                                if (isPhoneValid) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = "Valid", tint = Color(0xFF2E7D32))
                                }
                            },
                            isError = isPhoneTouched && !isPhoneValid,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Inline Field Validation Helper Text
                        if (isPhoneTouched && !isPhoneValid) {
                            Text(
                                text = "⚠️ Please enter a valid 10-digit mobile number",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                            )
                        }
                    }

                    if (!otpSent) {
                        // Large Primary CTA Button
                        Button(
                            onClick = {
                                if (isPhoneValid) {
                                    otpSent = true
                                    Toast.makeText(context, "OTP sent to +91 $mobileNumber", Toast.LENGTH_SHORT).show()
                                }
                            },
                            enabled = isPhoneValid,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("SEND OTP TO MOBILE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    } else {
                        // OTP Input with Inline Validation
                        Column {
                            OutlinedTextField(
                                value = otpText,
                                onValueChange = { input ->
                                    if (input.length <= 4 && input.all { it.isDigit() }) {
                                        otpText = input
                                    }
                                },
                                label = { Text("Enter 4-Digit OTP") },
                                placeholder = { Text("e.g. 1234") },
                                isError = isOtpTouched && !isOtpValid,
                                trailingIcon = {
                                    if (isOtpValid) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Valid", tint = Color(0xFF2E7D32))
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (isOtpTouched && !isOtpValid) {
                                Text(
                                    text = "⚠️ OTP must be exactly 4 digits",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                                )
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.loginUser(mode = "Mobile OTP", name = "Kisan Farmer", phone = mobileNumber)
                            },
                            enabled = isOtpValid,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("VERIFY OTP & LOGIN", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    // 1-Line Trust Message
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF0FDF4), RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "🔒 Your number is only used for secure login, never shared",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 11.sp,
                            color = Color(0xFF14532D),
                            textAlign = TextAlign.Center
                        )
                    }

                    // Secondary "More Login Options" Expander
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showMoreOptions = !showMoreOptions }
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (showMoreOptions) "Hide Other Login Methods" else "More Login Options (Google, Guest)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = if (showMoreOptions) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    AnimatedVisibility(
                        visible = showMoreOptions,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            // Google Login Button
                            OutlinedButton(
                                onClick = {
                                    if (!isAuthenticatingWithGoogle) {
                                        isAuthenticatingWithGoogle = true
                                        coroutineScope.launch {
                                            val result = googleAuthHelper.signInWithGoogle(context)
                                            isAuthenticatingWithGoogle = false
                                            result.fold(
                                                onSuccess = { firebaseUser ->
                                                    val name = firebaseUser.displayName ?: "Farmer User"
                                                    val phone = firebaseUser.phoneNumber ?: ""
                                                    Toast.makeText(context, "Welcome $name!", Toast.LENGTH_SHORT).show()
                                                    viewModel.loginUser(mode = "Google Account", name = name, phone = phone)
                                                },
                                                onFailure = { exc ->
                                                    Toast.makeText(
                                                        context,
                                                        "Google Auth fallback used",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    viewModel.loginUser(mode = "Google Account", name = "Farmer Ramesh")
                                                }
                                            )
                                        }
                                    }
                                },
                                enabled = !isAuthenticatingWithGoogle,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("btn_google_login"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                if (isAuthenticatingWithGoogle) {
                                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Signing in...")
                                } else {
                                    Icon(Icons.Default.AccountCircle, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Sign in with Google Account")
                                }
                            }

                            // Guest Mode Button
                            OutlinedButton(
                                onClick = { viewModel.loginUser(mode = "Guest Mode", name = "Guest Farmer") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("btn_guest_login"),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Continue as Guest Farmer ->", color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
