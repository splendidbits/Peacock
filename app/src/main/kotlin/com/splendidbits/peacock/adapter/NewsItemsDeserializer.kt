package com.splendidbits.peacock.adapter

import android.content.Context
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.splendidbits.peacock.R
import com.splendidbits.peacock.enums.NewsType
import com.splendidbits.peacock.model.NewsItem
import com.splendidbits.peacock.model.TrendingBatch
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class NewsItemsDeserializer(private val appContext: Context) : JsonDeserializer<TrendingBatch> {

    // Gson extension that makes things SO CLEAR.
    fun com.google.gson.JsonObject.string(name: String): String = get(name)?.asString ?: ""
    fun com.google.gson.JsonObject.boolean(name: String): Boolean = get(name)?.asBoolean ?: false

    override fun deserialize(root: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): TrendingBatch {
        val json = root?.asJsonArray?.get(0) as JsonObject

        val trendingBatch = TrendingBatch()
        trendingBatch.dateSaved = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        trendingBatch.groupId = json.string("id")
        trendingBatch.groupType = json.string("type")
        trendingBatch.header = json.string("header")
        trendingBatch.showMore = json.boolean("showMore")

        val items = json.getAsJsonArray("items") ?: listOf<JsonElement>()

        var firstBreakingItem = -1
        var firstRegularItem = -1
        for ((index, item) in items.withIndex()) {

            if (item is JsonObject) {
                val newsItem = NewsItem()

                newsItem.itemId = item.string("id")
                newsItem.url = item.string("url")
                newsItem.headline = item.string("headline")
                newsItem.summary = item.string("summary")
                newsItem.label = item.string("label")
                newsItem.breaking = item.asJsonObject?.get("breaking")?.asBoolean ?: false

                val dateFormat = appContext.getString(R.string.trending_date_format)
                newsItem.published = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                newsItem.published.time = SimpleDateFormat(dateFormat, Locale.US).parse(item.string("published"))

                if (item.asJsonObject?.get("tease")?.isJsonPrimitive!!) {
                    newsItem.tease = item.string("tease")
                }

                // Set the enum, video and slideshow properties.
                val itemTypeString = item.string("type")
                if (NewsType.TYPE_ARTICLE.typeName == itemTypeString && newsItem.breaking) {
                    newsItem.type = NewsType.TYPE_FEATURED

                    if (firstBreakingItem == -1) {
                        firstBreakingItem = index
                        firstRegularItem = index + 0
                    }

                } else if (NewsType.TYPE_ARTICLE.typeName == itemTypeString) {
                    newsItem.type = NewsType.TYPE_ARTICLE

                } else if (NewsType.TYPE_VIDEO.typeName == itemTypeString) {
                    newsItem.type = NewsType.TYPE_VIDEO
                    newsItem.duration = item.string("duration")
                    newsItem.videoPreviewUrl = item.string("preview")
                    newsItem.videoUrl = item.string("videoUrl")

                } else if (itemTypeString == NewsType.TYPE_SLIDESHOW.typeName) {
                    newsItem.type = NewsType.TYPE_SLIDESHOW
                }

                trendingBatch.newsItems += newsItem
            }
        }

        // Make at least one row featured if there are no breaking stories.
        if (!trendingBatch.newsItems.any { it.type == NewsType.TYPE_FEATURED }) {
            trendingBatch.newsItems[0].type = NewsType.TYPE_FEATURED
        }

        // Return breaking stories items on top.
        trendingBatch.newsItems = trendingBatch.newsItems.sortedWith(compareByDescending(NewsItem::published))

        // Every time we get even a single new item, we want to add it to a new group in the DB.
        // This is so we can do interesting things with history, the DAO, and because the API doesn't
        // support unique IDs for collections of items at the root level :-(
        trendingBatch.batchId = (trendingBatch.groupId + Math.abs(trendingBatch.newsItems.hashCode()))


        trendingBatch.newsItems.forEachIndexed({index, item ->
            item.batchId = trendingBatch.batchId

            // Move the first featured item to the top.
            if (firstBreakingItem == -1 && trendingBatch.newsItems.isNotEmpty()) {
                firstBreakingItem = index
                firstRegularItem = index + 1
                Collections.swap(trendingBatch.newsItems, firstRegularItem, firstRegularItem);
            }
        })

        return trendingBatch
    }
}