package com.splendidbits.peacocknews.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils

@Parcelize
@Entity(tableName = "tags", primaryKeys = ["tagId"])
data class Tag(var tagId: String = StringUtils.EMPTY,
               var label: String = StringUtils.EMPTY,
               var url: String = StringUtils.EMPTY) : Parcelable