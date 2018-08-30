package com.splendidbits.peacocknews.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils

@Parcelize
@Entity(tableName = "batch_item", primaryKeys = ["batchId", "itemId"])
data class BatchItem(var batchId: String = StringUtils.EMPTY,
                     var itemId: String = StringUtils.EMPTY) : Parcelable

