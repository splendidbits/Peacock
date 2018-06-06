package com.splendidbits.peacock.dao

import android.content.Context
import com.google.gson.GsonBuilder
import com.splendidbits.peacock.R
import com.splendidbits.peacock.adapter.NewsItemsDeserializer
import com.splendidbits.peacock.model.TrendingBatch
import com.splendidbits.peacock.service.RemoteApiService
import com.splendidbits.peacock.service.RetrofitLiveData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository(val context: Context, private val okHttpClient: OkHttpClient) {

    fun getLatestApiTrendingItems(): RetrofitLiveData<TrendingBatch> {
        return RetrofitLiveData(create(context.getString(R.string.trending_content_url)).getTrendingStories())
    }

    private fun create(baseUrl: String): RemoteApiService {
        val gson = GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(TrendingBatch::class.java, NewsItemsDeserializer(context))
                .create()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()

        return retrofit.create(RemoteApiService::class.java)
    }
}