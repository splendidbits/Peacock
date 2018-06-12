package com.splendidbits.peacock.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils

@Parcelize
@Entity(tableName = "batch_item", primaryKeys = ["batchId", "itemId"])
data class BatchItem(var batchId: String = StringUtils.EMPTY,
                     var itemId: String = StringUtils.EMPTY) : Parcelable

