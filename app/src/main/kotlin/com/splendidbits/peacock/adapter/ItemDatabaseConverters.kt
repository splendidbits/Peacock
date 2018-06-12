package com.splendidbits.peacock.adapter

import android.arch.persistence.room.TypeConverter
import com.splendidbits.peacock.enums.AssetType
import com.splendidbits.peacock.enums.ItemType
import java.text.SimpleDateFormat
import java.util.*


class ItemDatabaseConverters {
    // It's not worth injecting this WITH a context just for the string. Revisit?
    private var dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    @TypeConverter
    fun toItemType(value: String?): ItemType? {
        return when (value) {
            ItemType.TYPE_ARTICLE.typeName -> ItemType.TYPE_ARTICLE
            ItemType.TYPE_PAPER.typeName -> ItemType.TYPE_PAPER
            else -> ItemType.TYPE_NONE
        }
    }

    @TypeConverter
    fun fromItemType(itemType: ItemType?): String? {
        return itemType?.typeName
    }

    @TypeConverter
    fun toAssetType(value: String?): AssetType? {
        return when (value) {
            AssetType.TYPE_IMAGE.typeName -> AssetType.TYPE_IMAGE
            AssetType.TYPE_VIDEO.typeName -> AssetType.TYPE_VIDEO
            else -> AssetType.TYPE_NONE
        }
    }

    @TypeConverter
    fun fromAssetType(assetType: AssetType?): String? {
        return assetType?.typeName
    }

    @TypeConverter
    fun toCalendar(value: String?): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        if (value != null && value.isNotEmpty()) {
            val dateFormatter = SimpleDateFormat(dateFormat, Locale.US)
            calendar.time = dateFormatter.parse(value)
        }
        return calendar
    }

    @TypeConverter
    fun fromCalendar(calendar: Calendar?): String? {
        val dateFormatter = SimpleDateFormat(dateFormat, Locale.US)
        if (calendar != null) {
            return dateFormatter.format(calendar.time)
        }
        return dateFormatter.format(Date())
    }
}