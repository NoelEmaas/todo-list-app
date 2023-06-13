package com.codearmy.todolistapp.domain.model

data class HourlyData(
    val temperature_2m: Double,
    val relativehumidity_2m: Double,
    val precipitation: Double,
    val weathercode: Int
)

data class HourlyUnits(
    val time: String,
    val temperature_2m: String,
    val relativehumidity_2m: String,
    val precipitation: String,
    val weathercode: String
)

data class Hourly(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val relativehumidity_2m: List<Double>,
    val precipitation: List<Double>,
    val weathercode: List<Int>
)

data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Int,
    val hourly_units: HourlyUnits,
    val hourly: Hourly
)