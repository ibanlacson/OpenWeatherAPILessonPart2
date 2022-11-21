package com.auf.cea.openweatherapilesson.models.geocode

data class GeoCodingModelItem(
    var country: String,
    var lat: Double,
    var local_names: LocalNames,
    var lon: Double,
    var name: String
)