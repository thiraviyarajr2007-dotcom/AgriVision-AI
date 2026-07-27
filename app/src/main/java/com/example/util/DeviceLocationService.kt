package com.example.util

import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import java.util.Locale

data class AutoDetectedLocation(
    val cityName: String,
    val stateName: String,
    val fullLocationLabel: String,
    val detectedLanguage: String, // "Tamil" or "Kannada"
    val isTamilNadu: Boolean,
    val isKarnataka: Boolean,
    val latitude: Double,
    val longitude: Double
)

object DeviceLocationService {

    fun hasLocationPermission(context: Context): Boolean {
        val finePermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarsePermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return finePermission || coarsePermission
    }

    fun detectDeviceLocation(context: Context): AutoDetectedLocation {
        var lastLoc: Location? = null
        if (hasLocationPermission(context)) {
            try {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
                if (locationManager != null) {
                    val providers = locationManager.getProviders(true)
                    for (provider in providers) {
                        @Suppress("MissingPermission")
                        val loc = locationManager.getLastKnownLocation(provider)
                        if (loc != null) {
                            if (lastLoc == null || loc.accuracy < lastLoc.accuracy) {
                                lastLoc = loc
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                // Ignore hardware/permission exceptions
            }
        }

        // Try geocoding if location coordinates are present
        if (lastLoc != null) {
            try {
                val geocoder = Geocoder(context, Locale.ENGLISH)
                @Suppress("DEPRECATION")
                val addresses: List<Address>? = geocoder.getFromLocation(lastLoc.latitude, lastLoc.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val adminArea = address.adminArea ?: ""
                    val locality = address.locality ?: address.subAdminArea ?: "Farm Zone"

                    return buildLocationResult(
                        city = locality,
                        state = adminArea,
                        lat = lastLoc.latitude,
                        lon = lastLoc.longitude
                    )
                }
            } catch (e: Exception) {
                // Geocoder non-responsive or offline
            }
        }

        // Default region detection
        val defaultLocale = Locale.getDefault().toString()
        if (defaultLocale.contains("kn") || defaultLocale.contains("Kannada")) {
            return buildLocationResult("Bengaluru", "Karnataka", 12.9716, 77.5946)
        }

        return buildLocationResult("Coimbatore", "Tamil Nadu", 11.0168, 76.9558)
    }

    fun buildLocationResult(
        city: String,
        state: String,
        lat: Double,
        lon: Double
    ): AutoDetectedLocation {
        val cleanState = when {
            state.contains("Karnataka", ignoreCase = true) || state.contains("Kanada", ignoreCase = true) -> "Karnataka"
            state.contains("Tamil", ignoreCase = true) || state.contains("TN", ignoreCase = true) -> "Tamil Nadu"
            else -> if (state.isNotBlank()) state else "Tamil Nadu"
        }

        val isTN = cleanState.equals("Tamil Nadu", ignoreCase = true)
        val isKA = cleanState.equals("Karnataka", ignoreCase = true)

        val detectedLang = when {
            isKA -> "Kannada"
            isTN -> "Tamil"
            else -> "Tamil"
        }

        val cleanCity = if (city.isBlank() || city.equals("Farm Zone", ignoreCase = true)) {
            if (isKA) "Bengaluru" else "Coimbatore"
        } else city

        val fullLabel = "$cleanCity, $cleanState"

        return AutoDetectedLocation(
            cityName = cleanCity,
            stateName = cleanState,
            fullLocationLabel = fullLabel,
            detectedLanguage = detectedLang,
            isTamilNadu = isTN,
            isKarnataka = isKA,
            latitude = lat,
            longitude = lon
        )
    }
}
