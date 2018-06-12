package com.splendidbits.peacock.dao

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.splendidbits.peacock.R
import com.splendidbits.peacock.adapter.NewsItemsDeserializer
import com.splendidbits.peacock.model.Batch
import com.splendidbits.peacock.service.RemoteApiService
import com.splendidbits.peacock.service.RetrofitLiveData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository(val context: Context, private val okHttpClient: OkHttpClient, val articlesDao: ArticlesDao) {

    private fun getLiveTrending(): LiveData<Batch> {
        val retrofitCall = create(context.getString(R.string.latest_feed_url)).getCoverItems()
        return RetrofitLiveData(retrofitCall)
//                .map { batch: Batch ->
//            saveBatch(batch)
//            batch
//        }
    }

    private fun getSavedTrending(): LiveData<Batch?> {
        val liveData = MutableLiveData<Batch>()
//        val batchLiveData = Transformations.switchMap(articlesDao.getLatestBatch(), { batch ->
//            articlesDao.getItems(batch?.batchId)
//                    .map {
//                        batch?.items = it
//                        batch
//                    }
//        })
        return liveData
    }

    fun getLatestBatch(): LiveData<Batch> {
        val liveDataMerger = MediatorLiveData<Batch>()
        liveDataMerger.addSource(getSavedTrending(), {
            liveDataMerger.setValue(it)
        })
        liveDataMerger.addSource(getLiveTrending(), {
            liveDataMerger.setValue(it)
        })
        return liveDataMerger
    }

    @SuppressLint("CheckResult")
    fun saveBatch(batch: Batch) {
        if (batch.batchId.isNotEmpty()) {
//            Observable.just(this)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe {
//                        batch.items.forEach({ item ->
//                            item.assets.forEach({ articlesDao.insertAsset(it, ItemAsset(itemId = item.itemId, assetId = it.assetId)) })
//                            item.tags.forEach({ articlesDao.insertTag(it, ItemTag(itemId = item.itemId, tagId = it.tagId)) })
//                            articlesDao.insertItem(item, BatchItem(itemId = item.itemId, batchId = batch.batchId))
//                        })
//                        articlesDao.insertBatch(batch)
//                    }
        }
    }

    fun clearAll() {
        articlesDao.clearBatches()
        articlesDao.clearBatchItems()
        articlesDao.clearItems()
        articlesDao.clearTags()
        articlesDao.clearItemTags()
        articlesDao.clearAssets()
        articlesDao.clearItemAssets()
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