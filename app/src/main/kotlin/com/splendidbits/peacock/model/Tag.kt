package com.splendidbits.peacock.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils

@Parcelize
@Entity(tableName = "tags", primaryKeys = ["tagId"])
data class Tag(var tagId: String = StringUtils.EMPTY,
               var label: String = StringUtils.EMPTY,
               var url: String = StringUtils.EMPTY) : Parcelable