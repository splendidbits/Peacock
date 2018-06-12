package com.splendidbits.peacock.model

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.os.Parcelable
import com.splendidbits.peacock.enums.ItemType
import kotlinx.android.parcel.Parcelize
import org.apache.commons.lang3.StringUtils
import java.util.*

@Parcelize
@Entity(tableName = "items", primaryKeys = ["itemId"])
data class Item(var itemId : String = StringUtils.EMPTY,
                var externalId : String = StringUtils.EMPTY,
                var type: ItemType = ItemType.TYPE_NONE,
                var featured: Boolean = false,
                var breaking: Boolean = false,
                var section: String = StringUtils.EMPTY,
                var subSection: String = StringUtils.EMPTY,
                var title: String = StringUtils.EMPTY,
                var summary: String = StringUtils.EMPTY,
                var content: String = StringUtils.EMPTY,
                @Ignore var tags: List<Tag> = mutableListOf(),
                @Ignore var assets: List<Asset> = mutableListOf(),
                var url: String = StringUtils.EMPTY,
                var published: Calendar = Calendar.getInstance(),
                var publishedFirst: Calendar = Calendar.getInstance()) : ViewModel(), Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other is Item) {
            return itemId == other.itemId
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return itemId.hashCode()
    }
}