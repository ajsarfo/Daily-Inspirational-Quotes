package com.cardnotes.inspirationalquotes.application.image

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStore @Inject constructor(
    @ApplicationContext context: Context
) {

    private val gradientImages: Set<String> = context
        .assets
        .list(GRADIENT_IMAGE_FOLDER)!!
        .map { it.toLowerCase(Locale.ENGLISH) }
        .toHashSet()

    private val pictureImages: Set<String> = context
        .assets
        .list(PICTURE_IMAGE_FOLDER)!!
        .map { it.toLowerCase(Locale.ENGLISH) }
        .toHashSet()

    fun pictureImageUris(): List<Uri> = pictureImages.map { it.toAssetUri(PICTURE_IMAGE_FOLDER) }

    fun gradientImageUris() : List<Uri> = gradientImages.map { it.toAssetUri(GRADIENT_IMAGE_FOLDER) }

    fun iconImageUris(context: Context) : List<Uri> {
        return context.assets
            .list(ICON_IMAGE_FOLDER)!!
            .map { it.toLowerCase(Locale.ENGLISH) }
            .toHashSet()
            .map { it.toAssetUri(ICON_IMAGE_FOLDER) }
    }

    fun iconImage(name: String) : Uri {
        return name.toAssetUri(ICON_IMAGE_FOLDER)
    }

    fun randomGradientImage() : Uri {
        return gradientImages.random().toAssetUri(GRADIENT_IMAGE_FOLDER)
    }

    fun randomPictureImage() : Uri {
        return pictureImages.random().toAssetUri(PICTURE_IMAGE_FOLDER)
    }
}
