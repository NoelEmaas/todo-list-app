package com.codearmy.todolistapp.data

import com.codearmy.todolistapp.domain.model.Address
import com.codearmy.todolistapp.domain.model.Location
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

object LocationDataSource {

    fun getLocation(lat: Double, long: Double, callback: (Address?) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://nominatim.openstreetmap.org/reverse?format=json&lat=$lat&lon=$long")
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val locationData = gson.fromJson(body, Location::class.java)
                callback(locationData.address)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("failed to retrieve location")
                callback(null)
            }
        })
    }

}