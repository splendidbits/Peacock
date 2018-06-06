package com.splendidbits.peacock.adapter

import android.arch.persistence.room.TypeConverter
import com.splendidbits.peacock.enums.NewsType
import java.text.SimpleDateFormat
import java.util.*


class NewsTypeConverters {
    // It's not worth injecting this WITH a context just for the string. Revisit?
    private var dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    @TypeConverter
    fun toNewsItemType(value: String?): NewsType? {
        return when (value) {
            NewsType.TYPE_FEATURED.typeName -> NewsType.TYPE_FEATURED
            NewsType.TYPE_ARTICLE.typeName -> NewsType.TYPE_ARTICLE
            NewsType.TYPE_VIDEO.typeName -> NewsType.TYPE_VIDEO
            NewsType.TYPE_SLIDESHOW.typeName -> NewsType.TYPE_SLIDESHOW
            else -> null
        }
    }

    @TypeConverter
    fun fromNewsItemType(type: NewsType?): String? {
        return type?.typeName
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