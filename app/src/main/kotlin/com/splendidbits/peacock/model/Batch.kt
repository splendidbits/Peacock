package com.splendidbits.peacock.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import java.util.*

@Parcelize
@Entity(tableName = "batches", primaryKeys = ["batchId"])
data class Batch(var batchId: String = StringUtils.EMPTY,
                 var title: String = StringUtils.EMPTY,
                 var orgName: String = StringUtils.EMPTY,
                 var orgUrl: String = StringUtils.EMPTY,
                 var updated: Calendar = Calendar.getInstance(),
                 @Ignore
                 var items: List<Item> = mutableListOf(),
                 var saved: Calendar = Calendar.getInstance()) : ViewModel(), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is Batch) {
            return batchId == other.batchId
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return batchId.hashCode()
    }
}
