package com.codearmy.todolistapp.data

import com.codearmy.todolistapp.domain.model.HourlyData
import com.codearmy.todolistapp.domain.model.WeatherData
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

object WeatherDataSource {

    fun getWeatherData(lat: Double, long: Double, callback: (Map<String, HourlyData>?) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${long}&hourly=temperature_2m,relativehumidity_2m,precipitation,weathercode")
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, WeatherData::class.java)
                val weatherData = mapHourlyData(data)
                callback(weatherData)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("failed to retrieve weather")
                callback(null)
            }
        })
    }

    fun mapHourlyData(data: WeatherData): Map<String, HourlyData> {
        val hourlyMap = mutableMapOf<String, HourlyData>()
        val length = data.hourly.time.size
        for (i in 0 until length) {
            val time = data.hourly.time[i]
            val temperature = data.hourly.temperature_2m[i]
            val humidity = data.hourly.relativehumidity_2m[i]
            val precipitation = data.hourly.precipitation[i]
            val weathercode = data.hourly.weathercode[i]
            hourlyMap[time] = HourlyData(temperature, humidity, precipitation, weathercode)
        }
        return hourlyMap
    }

}