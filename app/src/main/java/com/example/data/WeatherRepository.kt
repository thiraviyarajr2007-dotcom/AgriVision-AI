package com.example.data

import com.example.data.model.AgriWeatherAdvisory
import com.example.data.model.DailyForecast
import com.example.data.model.HourlyForecast
import com.example.data.model.HyperLocalWeatherData
import com.example.data.model.WeatherLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherRepository {

    val presetLocations = listOf(
        WeatherLocation(
            name = "Coimbatore",
            district = "Coimbatore District",
            state = "Tamil Nadu",
            lat = 11.0168,
            lon = 76.9558,
            primaryCrops = listOf("Cotton", "Tomato", "Maize", "Vegetables")
        ),
        WeatherLocation(
            name = "Thanjavur",
            district = "Delta District",
            state = "Tamil Nadu",
            lat = 10.7870,
            lon = 79.1378,
            primaryCrops = listOf("Paddy / Rice", "Sugarcane", "Blackgram", "Coconut")
        ),
        WeatherLocation(
            name = "Madurai",
            district = "Madurai District",
            state = "Tamil Nadu",
            lat = 9.9252,
            lon = 78.1198,
            primaryCrops = listOf("Chilli", "Jasmine", "Millets", "Cotton")
        ),
        WeatherLocation(
            name = "Salem",
            district = "Salem District",
            state = "Tamil Nadu",
            lat = 11.6643,
            lon = 78.1460,
            primaryCrops = listOf("Tapioca", "Mango", "Groundnut", "Turmeric")
        ),
        WeatherLocation(
            name = "Bengaluru",
            district = "Bengaluru Urban",
            state = "Karnataka",
            lat = 12.9716,
            lon = 77.5946,
            primaryCrops = listOf("Ragi / Finger Millet", "Tomato", "Flowers", "Arecanut")
        ),
        WeatherLocation(
            name = "Mysuru",
            district = "Mysuru District",
            state = "Karnataka",
            lat = 12.2958,
            lon = 76.6394,
            primaryCrops = listOf("Paddy", "Sugarcane", "Tobacco", "Coconut")
        ),
        WeatherLocation(
            name = "Karnal",
            district = "Karnal District",
            state = "Haryana",
            lat = 29.6857,
            lon = 76.9905,
            primaryCrops = listOf("Basmati Rice", "Wheat", "Mustard")
        ),
        WeatherLocation(
            name = "Rajkot",
            district = "Saurashtra Region",
            state = "Gujarat",
            lat = 22.3039,
            lon = 70.8022,
            primaryCrops = listOf("Groundnut", "Cotton", "Cumin")
        )
    )

    suspend fun fetchWeatherForLocation(location: WeatherLocation): HyperLocalWeatherData = withContext(Dispatchers.IO) {
        try {
            val urlString = "https://api.open-meteo.com/v1/forecast?latitude=${location.lat}&longitude=${location.lon}&current=temperature_2m,relative_humidity_2m,apparent_temperature,precipitation,rain,weather_code,surface_pressure,wind_speed_10m,wind_direction_10m,is_day&hourly=temperature_2m,relative_humidity_2m,soil_temperature_0cm,soil_moisture_0_to_1cm,precipitation_probability&daily=temperature_2m_max,temperature_2m_min,uv_index_max,precipitation_sum,precipitation_probability_max&timezone=auto"
            
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if (connection.responseCode == 200) {
                val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
                val root = JSONObject(jsonString)
                val current = root.getJSONObject("current")
                val daily = root.optJSONObject("daily")
                val hourly = root.optJSONObject("hourly")

                val temp = current.optDouble("temperature_2m", 28.5)
                val feelsLike = current.optDouble("apparent_temperature", 30.2)
                val humidity = current.optInt("relative_humidity_2m", 72)
                val windKmh = current.optDouble("wind_speed_10m", 12.4)
                val windDeg = current.optInt("wind_direction_10m", 180)
                val rainMm = current.optDouble("precipitation", 0.0)
                val code = current.optInt("weather_code", 0)
                val isDay = current.optInt("is_day", 1) == 1

                val windDirStr = when (windDeg) {
                    in 0..22, in 338..360 -> "N"
                    in 23..67 -> "NE"
                    in 68..112 -> "E"
                    in 113..157 -> "SE"
                    in 158..202 -> "S"
                    in 203..247 -> "SW"
                    in 248..292 -> "W"
                    else -> "NW"
                }

                // Daily metrics
                val rainProb = if (daily != null && daily.has("precipitation_probability_max")) {
                    daily.getJSONArray("precipitation_probability_max").optInt(0, 20)
                } else 20

                val uvMax = if (daily != null && daily.has("uv_index_max")) {
                    daily.getJSONArray("uv_index_max").optDouble(0, 6.5)
                } else 6.5

                // Soil metrics from hourly
                val soilTemp = if (hourly != null && hourly.has("soil_temperature_0cm")) {
                    hourly.getJSONArray("soil_temperature_0cm").optDouble(0, temp - 1.5)
                } else temp - 1.5

                val soilMoistureRaw = if (hourly != null && hourly.has("soil_moisture_0_to_1cm")) {
                    hourly.getJSONArray("soil_moisture_0_to_1cm").optDouble(0, 0.28)
                } else 0.28
                val soilMoisturePct = (soilMoistureRaw * 100).toInt().coerceIn(10, 95)

                val conditionText = parseWeatherCode(code)

                // Build hourly forecast list (next 6 hours)
                val hourlyList = mutableListOf<HourlyForecast>()
                if (hourly != null && hourly.has("time")) {
                    val times = hourly.getJSONArray("time")
                    val temps = hourly.getJSONArray("temperature_2m")
                    val rainProbs = hourly.optJSONArray("precipitation_probability")
                    val currentTimeMillis = System.currentTimeMillis()
                    val sdfIndex = SimpleDateFormat("yyyy-MM-dd'T'HH:00", Locale.getDefault())
                    val hourSdf = SimpleDateFormat("ha", Locale.getDefault())

                    for (i in 0 until minOf(6, times.length())) {
                        val tStr = times.getString(i)
                        val tVal = temps.getDouble(i).toInt()
                        val rVal = rainProbs?.optInt(i, 15) ?: 15
                        val timeFormatted = try {
                            val parsedDate = sdfIndex.parse(tStr)
                            if (parsedDate != null) hourSdf.format(parsedDate) else "${(i + 8) % 12 + 1} PM"
                        } catch (e: Exception) {
                            "${(i + 8) % 12 + 1} PM"
                        }
                        hourlyList.add(
                            HourlyForecast(
                                timeLabel = timeFormatted,
                                tempC = tVal,
                                rainProbPercent = rVal,
                                iconType = if (rVal > 60) "rain" else if (tVal > 32) "sunny" else "cloudy"
                            )
                        )
                    }
                } else {
                    hourlyList.addAll(generateFallbackHourly(temp.toInt()))
                }

                // Build 5-day forecast
                val dailyList = mutableListOf<DailyForecast>()
                if (daily != null && daily.has("time")) {
                    val days = daily.getJSONArray("time")
                    val maxs = daily.getJSONArray("temperature_2m_max")
                    val mins = daily.getJSONArray("temperature_2m_min")
                    val probs = daily.optJSONArray("precipitation_probability_max")
                    val daySdf = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
                    val dateParseSdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    for (i in 0 until minOf(5, days.length())) {
                        val dStr = days.getString(i)
                        val maxV = maxs.getDouble(i).toInt()
                        val minV = mins.getDouble(i).toInt()
                        val pVal = probs?.optInt(i, 20) ?: 20
                        val dayFormatted = try {
                            val parsedDate = dateParseSdf.parse(dStr)
                            if (parsedDate != null) daySdf.format(parsedDate) else "Day ${i + 1}"
                        } catch (e: Exception) {
                            "Day ${i + 1}"
                        }
                        dailyList.add(
                            DailyForecast(
                                dayLabel = dayFormatted,
                                maxTempC = maxV,
                                minTempC = minV,
                                rainProbPercent = pVal,
                                condition = if (pVal > 65) "Thunderstorm & Rain" else if (pVal > 35) "Scattered Showers" else "Partly Sunny"
                            )
                        )
                    }
                } else {
                    dailyList.addAll(generateFallbackDaily(temp.toInt()))
                }

                val advisory = generateAgriculturalAdvisory(
                    tempC = temp,
                    humidity = humidity,
                    windKmh = windKmh,
                    rainProbability = rainProb,
                    rainMm = rainMm,
                    soilMoisturePct = soilMoisturePct,
                    primaryCrops = location.primaryCrops
                )

                val lastUpdated = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

                return@withContext HyperLocalWeatherData(
                    location = location,
                    currentTempC = temp,
                    feelsLikeC = feelsLike,
                    humidityPercent = humidity,
                    windSpeedKmh = windKmh,
                    windDirection = windDirStr,
                    rainMm = rainMm,
                    rainProbability = rainProb,
                    uvIndex = uvMax,
                    soilTempC = soilTemp,
                    soilMoisturePercent = soilMoisturePct,
                    conditionText = conditionText,
                    weatherCode = code,
                    isNight = !isDay,
                    hourlyForecast = hourlyList,
                    dailyForecast = dailyList,
                    advisory = advisory,
                    lastUpdatedText = lastUpdated,
                    isLiveApi = true
                )
            } else {
                return@withContext getFallbackWeatherData(location, "API Response ${connection.responseCode}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext getFallbackWeatherData(location, "Offline / Cached Weather")
        }
    }

    private fun parseWeatherCode(code: Int): String {
        return when (code) {
            0 -> "Clear Sunny Sky"
            1, 2 -> "Partly Cloudy"
            3 -> "Overcast Sky"
            45, 48 -> "Foggy & Mist"
            51, 53, 55 -> "Light Drizzle"
            61, 63, 65 -> "Monsoon Rain"
            80, 81, 82 -> "Heavy Rain Showers"
            95, 96, 99 -> "Thunderstorm & Hail"
            else -> "Partly Sunny"
        }
    }

    private fun generateAgriculturalAdvisory(
        tempC: Double,
        humidity: Int,
        windKmh: Double,
        rainProbability: Int,
        rainMm: Double,
        soilMoisturePct: Int,
        primaryCrops: List<String>
    ): AgriWeatherAdvisory {
        val cropListText = primaryCrops.joinToString(", ")

        val (sprayStatus, sprayCond) = when {
            rainProbability > 60 || rainMm > 2.0 -> "UNSAFE" to "Rain expected ($rainProbability%). Pesticide / fungicide sprays will wash away. Postpone spraying for 24h."
            windKmh > 18.0 -> "WARNING" to "High wind speeds ($windKmh km/h) cause chemical spray drift. Spray during early morning (<8 AM)."
            humidity > 85 -> "WARNING" to "High humidity ($humidity%) reduces leaf absorption speed. Use a stickering adjuvant."
            else -> "SAFE" to "IDEAL SPRAY WINDOW: Low wind ($windKmh km/h) & low rain risk ($rainProbability%). Safe to spray organic Neem oil & foliar nutrients."
        }

        val irrigationAdvice = when {
            rainProbability > 60 || soilMoisturePct > 65 -> "SKIP IRRIGATION: Soil moisture is sufficient ($soilMoisturePct%) and high rain risk ($rainProbability%) will prevent root rot."
            soilMoisturePct < 30 -> "URGENT IRRIGATION REQUIRED: Soil moisture dropped to $soilMoisturePct%. Apply 2 hours drip irrigation for $cropListText."
            else -> "MODERATE IRRIGATION: Maintain regular drip schedule (30 mins in evening) to prevent moisture stress."
        }

        val harvestAdvice = when {
            rainProbability > 70 -> "HOLD HARVESTing: Postpone crop threshing & grain drying until rain clears."
            rainProbability < 25 && humidity < 70 -> "PRIME HARVEST WINDOW: Ideal low-humidity dry weather for harvesting & sun-drying $cropListText grains."
            else -> "MONITOR CROP DRYNESS: Ensure harvested crop is stored under tarpaulin sheets."
        }

        val pestRiskAlert = when {
            humidity > 80 && tempC in 22.0..31.0 -> "HIGH RISK (Fungal / Blight): High humidity ($humidity%) and warm temps trigger Rice Blast, Tomato Late Blight & Powdery Mildew. Inspect undersides of leaves."
            tempC > 34.0 -> "MODERATE RISK (Sucking Pests): Hot dry weather accelerates Aphid, Thrips & Whitefly multiplication on Cotton & Chilli."
            else -> "LOW PEST RISK: Normal weather conditions. Continue routine field inspection twice weekly."
        }

        val heatStress = when {
            tempC > 36.0 -> "SEVERE HEAT STRESS: Apply mulching around root zones & schedule micro-sprinklers to reduce ambient leaf temperature."
            tempC > 32.0 -> "MODERATE HEAT WARNING: Hydrate young saplings in the afternoon."
            else -> "OPTIMAL TEMPERATURE: Comfort zone for $cropListText crop photosynthesis."
        }

        return AgriWeatherAdvisory(
            sprayCondition = sprayCond,
            sprayStatus = sprayStatus,
            irrigationAdvice = irrigationAdvice,
            harvestAdvice = harvestAdvice,
            pestRiskAlert = pestRiskAlert,
            heatStressWarning = heatStress
        )
    }

    private fun generateFallbackHourly(baseTemp: Int): List<HourlyForecast> {
        return listOf(
            HourlyForecast("12 PM", baseTemp, 10, "sunny"),
            HourlyForecast("2 PM", baseTemp + 2, 15, "sunny"),
            HourlyForecast("4 PM", baseTemp + 1, 20, "cloudy"),
            HourlyForecast("6 PM", baseTemp - 1, 30, "cloudy"),
            HourlyForecast("8 PM", baseTemp - 3, 25, "cloudy"),
            HourlyForecast("10 PM", baseTemp - 4, 15, "cloudy")
        )
    }

    private fun generateFallbackDaily(baseTemp: Int): List<DailyForecast> {
        return listOf(
            DailyForecast("Today", baseTemp + 2, baseTemp - 5, 20, "Partly Sunny"),
            DailyForecast("Tomorrow", baseTemp + 1, baseTemp - 4, 45, "Scattered Showers"),
            DailyForecast("Thu", baseTemp + 3, baseTemp - 3, 15, "Clear Sky"),
            DailyForecast("Fri", baseTemp, baseTemp - 5, 60, "Monsoon Rain"),
            DailyForecast("Sat", baseTemp - 1, baseTemp - 6, 25, "Partly Cloudy")
        )
    }

    private fun getFallbackWeatherData(location: WeatherLocation, reasonNote: String): HyperLocalWeatherData {
        val baseTemp = when (location.name) {
            "Coimbatore" -> 31.0
            "Thanjavur" -> 33.5
            "Madurai" -> 34.0
            "Salem" -> 32.0
            "Karnal" -> 29.0
            else -> 30.0
        }

        val advisory = generateAgriculturalAdvisory(
            tempC = baseTemp,
            humidity = 74,
            windKmh = 14.0,
            rainProbability = 25,
            rainMm = 0.0,
            soilMoisturePct = 48,
            primaryCrops = location.primaryCrops
        )

        val lastUpdated = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        return HyperLocalWeatherData(
            location = location,
            currentTempC = baseTemp,
            feelsLikeC = baseTemp + 2.5,
            humidityPercent = 74,
            windSpeedKmh = 14.0,
            windDirection = "SW",
            rainMm = 0.0,
            rainProbability = 25,
            uvIndex = 7.2,
            soilTempC = baseTemp - 1.8,
            soilMoisturePercent = 48,
            conditionText = "Partly Cloudy • Tamil Nadu",
            weatherCode = 2,
            isNight = false,
            hourlyForecast = generateFallbackHourly(baseTemp.toInt()),
            dailyForecast = generateFallbackDaily(baseTemp.toInt()),
            advisory = advisory,
            lastUpdatedText = "$lastUpdated ($reasonNote)",
            isLiveApi = false
        )
    }
}
