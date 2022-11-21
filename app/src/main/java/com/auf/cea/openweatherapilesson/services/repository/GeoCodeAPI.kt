package com.auf.cea.openweatherapilesson.services.repository

import com.auf.cea.openweatherapilesson.models.geocode.GeoCodingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeAPI {
    @GET("/geo/1.0/direct")
    suspend fun getGeoCode(
        @Query("q") q : String,
        @Query("limit") limit : Int,
        @Query("appID") appID : String
    ) : Response<GeoCodingModel>
}