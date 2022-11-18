package com.auf.cea.openweatherapilesson.models

data class MainForecastModel(
    var cnt: Int,
    var cod: String,
    var message: Int,
    var city: CityModel,
    var list: List<ForecastModel>
): java.io.Serializable