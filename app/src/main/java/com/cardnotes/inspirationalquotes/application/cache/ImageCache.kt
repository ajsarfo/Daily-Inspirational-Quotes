package com.cardnotes.inspirationalquotes.application.cache

import android.net.Uri

interface ImageCache <T> {
    fun get(index: T) : Uri
    fun random() : Uri
    fun replace(index: T, uri: Uri)
}