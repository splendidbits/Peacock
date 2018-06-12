package com.splendidbits.peacock.helpers

import com.splendidbits.peacock.enums.ImageSize
import com.splendidbits.peacock.enums.ResizeMode
import org.apache.commons.lang3.StringUtils

private const val REGEX_EXTENDED_ATTRIBUTES: String = "\\.(([0-9]){2,};([0-9]){2,};([0-7]){1,};([0-9]){1,3};([0-5]){1,1}\\.\\w{3,3}|([0-9]){2,}%3[B]([0-9]){2,}%3[B]([0-7]){1,}%3[B]([0-9]){1,3}%3[B]([0-5]){1,1}\\.\\w{3,})"
const val DOMAIN_ORIGINAL: String = "s-nbcnews.com/i/"
const val DOMAIN_RESIZABLE: String = "s-nbcnews.com/j/"
private const val FOCAL_ATTRIBUTES_EXTENSION: String = ".focal-1000x500"

class ImageHelper {
    val sharpness = 7 // scale is 1-7
    val quality = 100 // percentage
    val IMAGE_THUMBNAIL_SIZE_WIDTH: Int = 256
    val IMAGE_THUMBNAIL_SIZE_HEIGHT: Int = 256
    val IMAGE_HERO_SIZE_WIDTH: Int = 1080
    val IMAGE_HERO_SIZE_HEIGHT: Int = 720

    fun getImageUrl(url: String, width: Int, height: Int, mode: ResizeMode): String {
        if (url.isEmpty() || (!url.contains(DOMAIN_ORIGINAL) && !url.contains(DOMAIN_RESIZABLE))) {
            return url
        }

        val lastDotPosition = url.lastIndexOf(".")
        val fileExtension = url.substring(lastDotPosition).toLowerCase()

        if (mode.equals(ResizeMode.ORIGINAL) || width < 1 || height < 1 || url.isEmpty()) {
            return revertUrlToOriginal(url, fileExtension)
                    .replace(DOMAIN_RESIZABLE, DOMAIN_ORIGINAL)
                    .replace(REGEX_EXTENDED_ATTRIBUTES, fileExtension)
                    .replace("http://", "https://")
        }

        if (width > 0 && height > 0) {
            val extendedAttributes = ".$width%3B$height%3B$sharpness%3B$quality%3B${mode.scaleInt}$fileExtension"
            return revertUrlToOriginal(url, fileExtension)
                    .replace(DOMAIN_ORIGINAL, DOMAIN_RESIZABLE)
                    .replace(fileExtension, extendedAttributes)
                    .replace("http://", "https://")
        }
        return url
    }

    fun getImageUrl(url: String, newSize: ImageSize): String {
        if (url.isEmpty() || (!url.contains(DOMAIN_ORIGINAL) && !url.contains(DOMAIN_RESIZABLE))) {
            return url
        }

        val lastDotPosition = url.lastIndexOf(".")
        val fileExtension = url.substring(lastDotPosition) //'.jpg', '.png', '.jpeg', etc

        return when (newSize) {
            ImageSize.THUMBNAIL -> {
                val width = IMAGE_THUMBNAIL_SIZE_WIDTH
                val height = IMAGE_THUMBNAIL_SIZE_HEIGHT
                val mode = ResizeMode.CROP_CENTER
                val quality = 70

                revertUrlToOriginal(url, fileExtension)
                        .replace(DOMAIN_ORIGINAL, DOMAIN_RESIZABLE)
                        .replace(fileExtension, ".$width%3B$height%3B$sharpness%3B$quality%3B${mode.scaleInt}$fileExtension")
                        .replace("http://", "https://")
            }

            ImageSize.ORIGINAL -> {
                revertUrlToOriginal(url, fileExtension)
            }

            ImageSize.FOCAL -> {
                revertUrlToOriginal(url, fileExtension)
                        .replace(DOMAIN_ORIGINAL, DOMAIN_RESIZABLE)
                        .replace(fileExtension, "$FOCAL_ATTRIBUTES_EXTENSION$fileExtension")
                        .replace("http://", "https://")
            }

            ImageSize.HERO -> {
                val width = IMAGE_HERO_SIZE_WIDTH
                val height = IMAGE_HERO_SIZE_HEIGHT
                val mode = ResizeMode.PRESERVE_ASPECT_RATIO

                revertUrlToOriginal(url, fileExtension)
                        .replace(DOMAIN_ORIGINAL, DOMAIN_RESIZABLE)
                        .replace(fileExtension, ".$width%3B$height%3B$sharpness%3B$quality%3B${mode.scaleInt}$fileExtension")
                        .replace("http://", "https://")
            }
        }
    }

    private fun revertUrlToOriginal(url: String, fileExtension: String): String {
        return url
                .replace(DOMAIN_RESIZABLE, DOMAIN_ORIGINAL)
                .replace(FOCAL_ATTRIBUTES_EXTENSION, StringUtils.EMPTY)
                .replace(Regex(REGEX_EXTENDED_ATTRIBUTES), fileExtension)
                .replace("http://", "https://")
    }
}