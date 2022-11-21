package com.auf.cea.openweatherapilesson.services.repository

import com.auf.cea.openweatherapilesson.models.currentweather.CurrentWeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherAPI {
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appId") appId:String,
        @Query("units") units:String
    ): Response <CurrentWeatherModel>
}