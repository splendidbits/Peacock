package com.splendidbits.peacocknews.helper

import android.net.Uri
import com.splendidbits.peacocknews.enums.AssetType
import com.splendidbits.peacocknews.enums.ImageSize
import com.splendidbits.peacocknews.model.Asset
import com.splendidbits.peacocknews.model.Item
import org.apache.commons.lang3.StringUtils

fun Item.hasImage(): Boolean {
    return assets.getFirstImageUrl() != null
}

fun Item.hasVideo(): Boolean {
    return assets.getFirstVideoUrl()?.isNotEmpty() ?: false &&
            Uri.parse(assets.getFirstVideoUrl()).isAbsolute
}

fun List<Asset>.getFirstImageUrl(): String? {
    forEach({ if (it.type == AssetType.TYPE_IMAGE && it.url.isNotEmpty()) return it.url })
    return null
}

fun List<Asset>.getHeroImage(): String? {
    val imageHelper = ImageHelper()
    forEach({ asset ->
        if (asset.type == AssetType.TYPE_IMAGE && asset.url.isNotEmpty()) {
            if (asset.url.toLowerCase().endsWith("1920x1080.jpg")) {
                return asset.url
            } else {
                return imageHelper.getImageUrl(asset.url, ImageSize.HERO)
            }
        }
    })
    return null
}

fun List<Asset>.getThumbnailImage(): String? {
    val imageHelper = ImageHelper()
    var staticImage: String = StringUtils.EMPTY
    var thumbImage: String = StringUtils.EMPTY

    for (asset in this) {
        if (asset.type == AssetType.TYPE_IMAGE && asset.url.isNotEmpty()) {
            if (asset.url.toLowerCase().contains("video")) {
                staticImage = asset.url
            } else {
                thumbImage = imageHelper.getImageUrl(asset.url, ImageSize.THUMBNAIL)
            }
            break
        }
    }

    return if (thumbImage.isNotEmpty()) thumbImage else if (staticImage.isNotEmpty()) staticImage else null
}

fun Any.logTag(): String = javaClass.simpleName

fun List<Asset>.getFirstVideoUrl(): String? {
    forEach({ if (it.type == AssetType.TYPE_VIDEO && it.url.isNotEmpty()) return it.url })
    return null
}

fun MutableList<Any?>.add(value: Any?) {
    if (value != null) {
        add(value)
    }
}