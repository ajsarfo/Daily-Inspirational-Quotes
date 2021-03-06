package com.cardnotes.inspirationalquotes.application.image

import android.graphics.Bitmap

interface BitmapCache {
    fun getBitmap(key: String) : Bitmap?
    fun saveBitmap(key: String, bitmap: Bitmap)
}