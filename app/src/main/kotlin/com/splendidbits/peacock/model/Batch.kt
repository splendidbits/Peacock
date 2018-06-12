package com.splendidbits.peacock.model

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import java.util.*

@Parcelize
@Entity(tableName = "batches", primaryKeys = ["batchId"])
data class Batch(var batchId: String = StringUtils.EMPTY,
                 var title: String = StringUtils.EMPTY,
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
