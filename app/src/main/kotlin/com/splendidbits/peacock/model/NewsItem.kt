package com.splendidbits.peacock.model

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Entity
import android.os.Parcelable
import com.splendidbits.peacock.enums.NewsType
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import java.util.*

@Parcelize
@Entity(tableName = "news_items", primaryKeys = ["itemId", "batchId"])
data class NewsItem(var itemId : String = StringUtils.EMPTY,
                    var batchId: String = StringUtils.EMPTY,
                    var type: NewsType = NewsType.TYPE_FEATURED,
                    var url: String = StringUtils.EMPTY,
                    var headline: String = StringUtils.EMPTY,
                    var published: Calendar = Calendar.getInstance(),
                    var tease: String = StringUtils.EMPTY,
                    var summary: String = StringUtils.EMPTY,
                    var label: String = StringUtils.EMPTY,
                    var body: String = StringUtils.EMPTY,
                    var breaking: Boolean = false,
                    var duration: String = StringUtils.EMPTY,
                    var videoPreviewUrl: String = StringUtils.EMPTY,
                    var videoUrl: String = videoPreviewUrl): ViewModel(), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is NewsItem) {
            return itemId == other.itemId
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return itemId.hashCode()
    }
}