package com.auf.cea.openweatherapilesson.models

data class CityModel(
    var coord: Coord,
    var country: String,
    var id: Int,
    var name: String,
    var population: Int,
    var sunrise: Long,
    var sunset: Long,
    var timezone: Int
):java.io.Serializable