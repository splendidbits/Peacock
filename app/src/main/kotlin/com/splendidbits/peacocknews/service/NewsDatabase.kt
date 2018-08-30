package com.splendidbits.peacocknews.service

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.splendidbits.peacocknews.adapter.ItemDatabaseConverters
import com.splendidbits.peacocknews.dao.ArticlesDao
import com.splendidbits.peacocknews.model.*

@Database(entities = [Item::class, Batch::class, Asset::class, Tag::class, BatchItem::class, ItemAsset::class, ItemTag::class], version = 5, exportSchema = false)
@TypeConverters(ItemDatabaseConverters::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun trendingRoomDao(): ArticlesDao

}