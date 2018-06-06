package com.splendidbits.peacock.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class TrendingBatchNewsItems {
    @Embedded
    lateinit var trendingBatch: TrendingBatch

    @Relation(parentColumn = "batchId", entityColumn = "batchId", entity = NewsItem::class)
    lateinit var newsItems: List<NewsItem>
}