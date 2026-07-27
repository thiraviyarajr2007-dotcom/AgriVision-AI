package com.example.data.model

data class WeatherLocation(
    val name: String,
    val district: String,
    val state: String,
    val lat: Double,
    val lon: Double,
    val primaryCrops: List<String>
)

data class HourlyForecast(
    val timeLabel: String,
    val tempC: Int,
    val rainProbPercent: Int,
    val iconType: String
)

data class DailyForecast(
    val dayLabel: String,
    val maxTempC: Int,
    val minTempC: Int,
    val rainProbPercent: Int,
    val condition: String
)

data class AgriWeatherAdvisory(
    val sprayCondition: String,
    val sprayStatus: String, // "SAFE", "WARNING", "UNSAFE"
    val irrigationAdvice: String,
    val harvestAdvice: String,
    val pestRiskAlert: String,
    val heatStressWarning: String
)

data class HyperLocalWeatherData(
    val location: WeatherLocation,
    val currentTempC: Double,
    val feelsLikeC: Double,
    val humidityPercent: Int,
    val windSpeedKmh: Double,
    val windDirection: String,
    val rainMm: Double,
    val rainProbability: Int,
    val uvIndex: Double,
    val soilTempC: Double,
    val soilMoisturePercent: Int,
    val conditionText: String,
    val weatherCode: Int,
    val isNight: Boolean,
    val hourlyForecast: List<HourlyForecast>,
    val dailyForecast: List<DailyForecast>,
    val advisory: AgriWeatherAdvisory,
    val lastUpdatedText: String,
    val isLiveApi: Boolean
)
