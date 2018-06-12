package com.splendidbits.peacock.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils

@Parcelize
@Entity(tableName = "item_tag", primaryKeys = ["itemId", "tagId"])
data class ItemTag(var itemId: String = StringUtils.EMPTY,
                   var tagId: String = StringUtils.EMPTY) : Parcelable

