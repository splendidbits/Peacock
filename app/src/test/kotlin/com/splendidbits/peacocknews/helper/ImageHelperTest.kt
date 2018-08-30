package com.splendidbits.peacocknews.helper

import com.splendidbits.peacocknews.enums.ImageSize
import com.splendidbits.peacocknews.enums.ResizeMode
import org.junit.Assert
import org.junit.Test

class ImageHelperTest {
    private val imageHelper = ImageHelper()

    @Test
    fun getResizeImageUrl() {
        val noAttributesUrl = "https://media1.s-nbcnews.com/i/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.jpg"
        val expectedHeroUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.${imageHelper.IMAGE_HERO_SIZE_WIDTH}%3B${imageHelper.IMAGE_HERO_SIZE_HEIGHT}%3B7%3B100%3B3.jpg"
        val heroImageUrl = imageHelper.getImageUrl(noAttributesUrl, ImageSize.HERO)
        Assert.assertEquals(expectedHeroUrl, heroImageUrl)

        val expectedHeroResizedUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.${imageHelper.IMAGE_THUMBNAIL_SIZE_WIDTH}%3B${imageHelper.IMAGE_THUMBNAIL_SIZE_HEIGHT}%3B7%3B100%3B5.jpg"
        val heroResizedUrl = imageHelper.getImageUrl(heroImageUrl, ImageSize.THUMBNAIL)
        Assert.assertEquals(heroResizedUrl, expectedHeroResizedUrl)

        val expectedFocalUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.focal-1000x500.jpg"
        val focalUrl = imageHelper.getImageUrl(heroResizedUrl, ImageSize.FOCAL)
        Assert.assertEquals(focalUrl, expectedFocalUrl)

        val clearedUrl = imageHelper.getImageUrl(focalUrl, ImageSize.ORIGINAL)
        Assert.assertEquals(noAttributesUrl, clearedUrl)

        val noAttributesUrlNonEncoded = "https://media1.s-nbcnews.com/i/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.jpeg"
        val expectedHeroUrlNonEncoded = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.${imageHelper.IMAGE_HERO_SIZE_WIDTH}%3B${imageHelper.IMAGE_HERO_SIZE_HEIGHT}%3B7%3B100%3B3.jpeg"
        val heroImageUrlNonEncoded = imageHelper.getImageUrl(noAttributesUrlNonEncoded, ImageSize.HERO)
        Assert.assertEquals(expectedHeroUrlNonEncoded, heroImageUrlNonEncoded)

        val expectedHeroResizedUrlNonEncoded = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.${imageHelper.IMAGE_THUMBNAIL_SIZE_WIDTH}%3B${imageHelper.IMAGE_THUMBNAIL_SIZE_HEIGHT}%3B7%3B100%3B5.jpeg"
        val heroResizedUrlNonEncoded = imageHelper.getImageUrl(heroImageUrlNonEncoded, ImageSize.THUMBNAIL)
        Assert.assertEquals(heroResizedUrlNonEncoded, expectedHeroResizedUrlNonEncoded)

        val expectedFocalUrlNonEncoded = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.focal-1000x500.jpeg"
        val focalUrlNonEncoded = imageHelper.getImageUrl(heroResizedUrlNonEncoded, ImageSize.FOCAL)
        Assert.assertEquals(focalUrlNonEncoded, expectedFocalUrlNonEncoded)

        val clearedUrlNonEncoded = imageHelper.getImageUrl(focalUrlNonEncoded, ImageSize.ORIGINAL)
        Assert.assertEquals(noAttributesUrlNonEncoded, clearedUrlNonEncoded)
    }

    @Test
    fun getResizeImageUrlOptions() {
        val noAttributesUrl = "https://media1.s-nbcnews.com/i/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.png"

        val expectedLetterboxOutsideUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.1200%3B1440%3B7%3B100%3B1.png"
        val letterboxOutsideUrl = imageHelper.getImageUrl(url = noAttributesUrl, width = 1200, height = 1440, mode = ResizeMode.LETTERBOX_OUTSIDE)
        Assert.assertEquals(expectedLetterboxOutsideUrl, letterboxOutsideUrl)

        val expectedCropLetterboxOutsideUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.1200%3B1440%3B7%3B100%3B2.png"
        val cropLetterboxOutsideUrl = imageHelper.getImageUrl(url = noAttributesUrl, width = 1200, height = 1440, mode = ResizeMode.CROP_LETTERBOX_OUTSIDE)
        Assert.assertEquals(expectedCropLetterboxOutsideUrl, cropLetterboxOutsideUrl)

        val expectedPreserveAspectRatioUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.1200%3B1440%3B7%3B100%3B3.png"
        val preserveAspectRatioUrl = imageHelper.getImageUrl(url = noAttributesUrl, width = 1200, height = 1440, mode = ResizeMode.PRESERVE_ASPECT_RATIO)
        Assert.assertEquals(expectedPreserveAspectRatioUrl, preserveAspectRatioUrl)

        val expectedCropTopUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.1200%3B1440%3B7%3B100%3B4.png"
        val cropTopUrl = imageHelper.getImageUrl(url = noAttributesUrl, width = 1200, height = 1440, mode = ResizeMode.CROP_TOP)
        Assert.assertEquals(expectedCropTopUrl, cropTopUrl)

        val expectedCropCenterUrl = "https://media1.s-nbcnews.com/j/newscms/2018_23/2459631/180608-donald-trump-g7-meeting-ew-552p_046dae2c6efb1d5cade039cdb1f0c902.1200%3B1440%3B7%3B100%3B5.png"
        val cropCentreUrl = imageHelper.getImageUrl(url = noAttributesUrl, width = 1200, height = 1440, mode = ResizeMode.CROP_CENTER)
        Assert.assertEquals(expectedCropCenterUrl, cropCentreUrl)

        val originalUrl = imageHelper.getImageUrl(url = noAttributesUrl, width = 1200, height = 1440, mode = ResizeMode.ORIGINAL)
        Assert.assertEquals(noAttributesUrl, originalUrl)

        val fromCropOriginalUrl = imageHelper.getImageUrl(url = cropCentreUrl, width = 1200, height = 1440, mode = ResizeMode.ORIGINAL)
        Assert.assertEquals(noAttributesUrl, fromCropOriginalUrl)
    }
}