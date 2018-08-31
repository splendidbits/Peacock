package com.splendidbits.peacock.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils

@Parcelize
@Entity(tableName = "item_asset", primaryKeys = ["itemId", "assetId"])
data class ItemAsset(var itemId: String = StringUtils.EMPTY,
                     var assetId: String = StringUtils.EMPTY) : Parcelable

