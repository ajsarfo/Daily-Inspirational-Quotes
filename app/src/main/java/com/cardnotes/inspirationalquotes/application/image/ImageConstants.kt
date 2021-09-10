package com.cardnotes.inspirationalquotes.application.image

import android.net.Uri

const val GRADIENT_IMAGE_FOLDER = "quote_gradients"
const val PICTURE_IMAGE_FOLDER = "quote_images"
const val ICON_IMAGE_FOLDER = "icons"

fun String.toAssetUri(folder: String) : Uri {
    return Uri.parse("file:///android_asset/$folder/$this")
}