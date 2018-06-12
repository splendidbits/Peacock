package com.splendidbits.peacock.service

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.splendidbits.peacock.adapter.ItemDatabaseConverters
import com.splendidbits.peacock.dao.ArticlesDao
import com.splendidbits.peacock.model.*

@Database(entities = [Item::class, Batch::class, Asset::class, Tag::class, BatchItem::class, ItemAsset::class, ItemTag::class], version = 5, exportSchema = false)
@TypeConverters(ItemDatabaseConverters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun trendingRoomDao(): ArticlesDao

}