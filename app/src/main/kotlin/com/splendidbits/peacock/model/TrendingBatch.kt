package com.splendidbits.peacock.model

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import java.util.*

@Parcelize
@Entity(tableName = "trending_batches", primaryKeys = ["batchId", "groupId"])
data class TrendingBatch(var batchId: String = StringUtils.EMPTY,
                         var groupId: String = StringUtils.EMPTY,
                         var groupType: String = StringUtils.EMPTY,
                         var header: String = StringUtils.EMPTY,
                         var showMore: Boolean = false,
                         var dateSaved: Calendar = Calendar.getInstance(),
                         @Ignore
                         var newsItems: List<NewsItem> = arrayListOf()): ViewModel(), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is TrendingBatch) {
            return batchId == other.batchId
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return batchId.hashCode()
    }
}
