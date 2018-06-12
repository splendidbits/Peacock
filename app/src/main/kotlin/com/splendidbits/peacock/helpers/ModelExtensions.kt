package com.splendidbits.peacock.helpers

import com.splendidbits.peacock.enums.AssetType
import com.splendidbits.peacock.model.Asset
import com.splendidbits.peacock.model.Item

fun Item.hasImage(): Boolean {
    return assets.getFirstImageUrl() != null
}

fun Item.hasVideo(): Boolean {
    return assets.getFirstVideoUrl() != null
}

fun List<Asset>.getFirstImageUrl(): String? {
    forEach({ if (it.type == AssetType.TYPE_IMAGE && it.url.isNotEmpty()) return it.url })
    return null
}

fun List<Asset>.getFirstVideoUrl(): String? {
    forEach({ if (it.type == AssetType.TYPE_VIDEO && it.url.isNotEmpty()) return it.url })
    return null
}