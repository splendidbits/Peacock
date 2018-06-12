package com.splendidbits.peacock.enums

enum class ImageSize(name: String) {
    THUMBNAIL("thumbnail"),
    ORIGINAL("regular"),
    FOCAL("focal"),
    HERO("cover");
}

enum class ResizeMode(val scaleInt: Int) {
    // Keep exactly as the file specifies
    ORIGINAL(0),

    // Letterboxes image to maintain original aspect ratio.
    // Does not crop/zoom but resizes image to maintain aspect ratio.
    LETTERBOX_OUTSIDE(1),

    // Sets the absolute width and height and crops any pixels outside (zoom).
    // Letterboxes to maintain aspect ratio.
    CROP_LETTERBOX_OUTSIDE(2),

    // Resizes the image width or height to maintain  aspect ratio
    PRESERVE_ASPECT_RATIO(3),

    // Crops the image by using the top edge point as a pivot. (keeps top part of image)
    // Does not maintain any aspect ratio.
    CROP_TOP(4),

    // Crops the image by using the center point as a pivot.(keeps center part of image)
    // Does not maintain any aspect ratio.
    CROP_CENTER(5),
}