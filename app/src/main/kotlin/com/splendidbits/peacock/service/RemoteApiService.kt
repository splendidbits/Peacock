package com.splendidbits.peacock.service

import com.splendidbits.peacock.model.Batch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteApiService {
    @GET("/portal/latest")
    fun getLatestItems(@Query("server") server: String = "prod",
                       @Query("mergetype") mergeType: String = "beta",
                       @Query("_devicefeed_") deviceFeed: String = "cover"): Call<Batch>

    @GET("/portal/cover/merged")
    fun getCoverItems(@Query("server") server: String = "prod",
                      @Query("mergetype") mergeType: String = "beta",
                      @Query("_devicefeed_") deviceFeed: String = "cover"): Call<Batch>
}