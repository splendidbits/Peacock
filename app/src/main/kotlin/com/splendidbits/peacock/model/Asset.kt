package com.splendidbits.peacock.model

import android.os.Parcelable
import androidx.room.Entity
import com.splendidbits.peacock.enums.AssetType
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import java.util.*

@Parcelize
@Entity(tableName = "assets", primaryKeys = ["assetId"])
data class Asset(var assetId: String = StringUtils.EMPTY,
                 var type: AssetType = AssetType.TYPE_NONE,
                 var altText: String = StringUtils.EMPTY,
                 var title: String = StringUtils.EMPTY,
                 var copyright: String = StringUtils.EMPTY,
                 var width: Int = 0,
                 var height: Int = 0,
                 var duration: String = StringUtils.EMPTY,
                 var url: String = StringUtils.EMPTY,
                 var published: Calendar = Calendar.getInstance(),
                 var saved: Calendar = Calendar.getInstance()) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is Asset) {
            return assetId == other.assetId
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return assetId.hashCode()
    }
}
