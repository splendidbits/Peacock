package com.splendidbits.peacock.dao

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.GsonBuilder
import com.splendidbits.peacock.R
import com.splendidbits.peacock.adapter.NewsItemsDeserializer
import com.splendidbits.peacock.helper.map
import com.splendidbits.peacock.model.Batch
import com.splendidbits.peacock.model.BatchItem
import com.splendidbits.peacock.model.ItemAsset
import com.splendidbits.peacock.model.ItemTag
import com.splendidbits.peacock.service.RemoteApiService
import com.splendidbits.peacock.service.RetrofitLiveData
import kotlinx.coroutines.experimental.async
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository(val context: Context, private val okHttpClient: OkHttpClient, val articlesDao: ArticlesDao) {

    private fun getLiveTrending(): LiveData<Batch> {
        val retrofitCall = create(context.getString(R.string.latest_feed_url)).getCoverItems()
        return RetrofitLiveData(retrofitCall)
                .map { batch: Batch ->
                    saveBatch(batch)
                    batch
                }
    }

    private fun getSavedTrending(): LiveData<Batch?> {
        return articlesDao.getLatestBatch()
                .map { batch ->
                    articlesDao.getItems(batch?.batchId).map { items ->
                        batch?.items = items
                    }
                    batch

                }.map { batch ->
                    batch?.items?.forEach { item ->
                        articlesDao.getItemTags(item.itemId).map { tags ->
                            item.tags = tags
                        }
                    }
                    batch

                }.map { batch ->
                    batch?.items?.forEach { item ->
                        articlesDao.getItemAssets(item.itemId).map { assets ->
                            item.assets = assets
                        }
                    }
                    batch
                }
    }

    fun getLatestBatch(): LiveData<Batches> {
        val liveDataMerger = MediatorLiveData<Batches>()
        liveDataMerger.addSource(getSavedTrending(), {
            liveDataMerger.setValue(Batches(it, DataSourceType.TYPE_SAVED))
        })
        liveDataMerger.addSource(getLiveTrending(), {
            liveDataMerger.setValue(Batches(it, DataSourceType.TYPE_LIVE))
        })
        return liveDataMerger
    }

    fun saveBatch(batch: Batch) {
        async {
            batch.items.forEach { item ->
                item.assets.forEach({ articlesDao.insertAsset(it, ItemAsset(itemId = item.itemId, assetId = it.assetId)) })
                item.tags.forEach({ articlesDao.insertTag(it, ItemTag(itemId = item.itemId, tagId = it.tagId)) })
                articlesDao.insertItem(item, BatchItem(itemId = item.itemId, batchId = batch.batchId))
            }
            articlesDao.insertBatch(batch)
            true
        }.start()
    }

    private fun create(baseUrl: String): RemoteApiService {
        val gson = GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(Batch::class.java, NewsItemsDeserializer(context))
                .create()

        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .build()

        return retrofit.create(RemoteApiService::class.java)
    }
}

enum class DataSourceType {
    TYPE_NONE,
    TYPE_SAVED,
    TYPE_LIVE
}

class Batches(val type: DataSourceType) {
    var batches: MutableList<Batch> = mutableListOf()
    var dataSourceType: DataSourceType = DataSourceType.TYPE_NONE

    constructor(batch: Batch?, type: DataSourceType) : this(type) {
        dataSourceType = type
        if (batch != null) {
            batches.add(batch)
        }
    }
}