package com.splendidbits.peacock.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils

@Parcelize
@Entity(tableName = "item_asset", primaryKeys = ["itemId", "assetId"])
data class ItemAsset(var itemId: String = StringUtils.EMPTY,
                     var assetId: String = StringUtils.EMPTY) : Parcelable

