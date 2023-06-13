package com.codearmy.todolistapp.presentation.screens.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.codearmy.todolistapp.data.WeatherDataSource
import com.codearmy.todolistapp.R
import com.codearmy.todolistapp.data.LocationDataSource
import java.time.ZoneId
import java.time.ZonedDateTime

data class HomeUiState(
    val activeButton: String = "todo",
    val hasNetworkAccess: Boolean = false,
    val hasLocationAccess: Boolean = false,
)

class HomeViewModel() : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    private val zoneId = ZoneId.of("GMT")
    private val zonedDateTime = ZonedDateTime.now(zoneId).toString().substring(0, 14) + "00"

    private var temperature: Double by mutableStateOf(0.0)
    private var humidity: Double by mutableStateOf(0.0)
    private var precipitation: Double by mutableStateOf(0.0)
    private var weatherCode: Int by mutableStateOf(0)

    private var city: String by mutableStateOf("")
    private var town: String by mutableStateOf("")
    private var state: String by mutableStateOf("")
    private var region: String by mutableStateOf("")

    fun getWeatherData(lat: Double, long: Double) {
        WeatherDataSource.getWeatherData(lat, long) { weatherData ->
            temperature = weatherData?.get(zonedDateTime)?.temperature_2m!!
            humidity = weatherData[zonedDateTime]?.relativehumidity_2m!!
            precipitation = weatherData[zonedDateTime]?.precipitation!!
            weatherCode = weatherData[zonedDateTime]?.weathercode!!
        }
    }

    fun getUserLocation(lat: Double, long: Double){
        LocationDataSource.getLocation(lat, long) { locationData ->
            if (locationData != null) {
                city = locationData.city
                town = locationData.town
                state = locationData.state
                region = locationData.region
            }
        }
    }

    fun getTypeOfWeather() : String {
        val weatherType = when(weatherCode) {
            0 ->  "Clear Sky"
            1, 2, 3 ->  "Cloudy"
            45, 48 ->  "Fog"
            51, 53, 55, 56, 57 -> "Drizzle"
            61, 63, 65, 66, 67 -> "Rainy"
            71, 73, 75, 77 -> "Snow"
            80, 81, 82 -> "Rain Shower"
            85, 86 -> "Snow"
            95, 96, 99 -> "Thunderstorm"
            else -> ""
        }
        return weatherType
    }

    fun getWeatherIcon() : Int {
        val icon = when(getTypeOfWeather()){
            "Clear Sky" -> R.drawable.clear_sky
            "Cloudy" -> R.drawable.weather_icon
            "Fog" -> R.drawable.foggy
            "Drizzle" -> R.drawable.drizzle
            "Rainy", "Rain Shower","Snow" -> R.drawable.rainy
            "Thunderstorm" -> R.drawable.thunderstorm
            else -> R.drawable.weather_icon
        }
        return icon
    }

    fun onActiveButtonChange(button : String) {
        homeUiState = homeUiState.copy(activeButton = button)
    }

    fun updateNetworkAccess(hasNetwork: Boolean) {
        homeUiState = homeUiState.copy(hasNetworkAccess = hasNetwork)
    }

    fun updateLocationAccess(hasLocation: Boolean) {
        homeUiState = homeUiState.copy(hasLocationAccess =  hasLocation)
    }

    fun getTemperature() : Double? {
        return temperature
    }

    fun getHumidity() : Double? {
        return humidity
    }

    fun getPrecipitation() : Double? {
        return precipitation
    }

    fun getLocation() : String {
        if(city != null) return "$city City, $state, $region"
        return "$town, $state, $region"
    }
}