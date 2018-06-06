package com.splendidbits.peacock.service

import com.splendidbits.peacock.model.TrendingBatch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteApiService {
    @GET("/v1/query/curation/news/")
    fun getTrendingStories(@Query("size") resultSize: Int = 30): Call<TrendingBatch>
}