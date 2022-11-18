package com.auf.cea.openweatherapilesson.models

data class Weather(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
):java.io.Serializable