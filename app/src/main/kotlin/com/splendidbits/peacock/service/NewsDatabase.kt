package com.splendidbits.peacock.service

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.splendidbits.peacock.adapter.NewsTypeConverters
import com.splendidbits.peacock.dao.TrendingRoomDao
import com.splendidbits.peacock.model.NewsItem
import com.splendidbits.peacock.model.TrendingBatch

@Database(entities = [NewsItem::class, TrendingBatch::class], version = 4, exportSchema = false)
@TypeConverters(NewsTypeConverters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun trendingRoomDao(): TrendingRoomDao

}