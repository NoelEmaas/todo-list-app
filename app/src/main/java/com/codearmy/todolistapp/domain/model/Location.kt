package com.codearmy.todolistapp.domain.model

data class Location(
    val place_id: Long,
    val licence: String,
    val osm_type: String,
    val osm_id: Long,
    val lat: Double,
    val lon: Double,
    val display_name: String,
    val address: Address,
    val boundingbox: List<String>
)

data class Address(
    val town: String,
    val city: String,
    val state: String,
    val ISO3166_2_lvl4: String,
    val region: String,
    val ISO3166_2_lvl3: String,
    val postcode: String,
    val country: String,
    val country_code: String
)