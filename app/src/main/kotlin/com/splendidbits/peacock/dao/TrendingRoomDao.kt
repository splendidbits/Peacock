package com.splendidbits.peacock.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.splendidbits.peacock.model.NewsItem
import com.splendidbits.peacock.model.TrendingBatch
import com.splendidbits.peacock.model.TrendingBatchNewsItems

@Dao
interface TrendingRoomDao {
    @Transaction
    @Query("select * from trending_batches order by datetime(dateSaved) desc LIMIT 1")
    fun getLatestSavedTrendingItems(): LiveData<TrendingBatchNewsItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingItems(newsItems: List<NewsItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingGroup(trendingBatch: TrendingBatch)

    @Transaction
    @Query("delete from trending_batches")
    fun clearTrending()
}