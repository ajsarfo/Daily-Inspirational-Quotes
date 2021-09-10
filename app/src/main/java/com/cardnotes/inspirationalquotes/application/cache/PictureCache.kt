package com.cardnotes.inspirationalquotes.application.cache

import android.net.Uri
import com.cardnotes.inspirationalquotes.application.image.ImageStore

class PictureCache<T>(private val imageStore: ImageStore) : ImageCache<T> {

    private val imageMap = hashMapOf<T, Uri>()

    override fun get(index: T): Uri {
        return imageMap.getOrPut(index) {
            imageStore.randomPictureImage()
        }
    }

    override fun random() : Uri {
        return imageStore.randomPictureImage()
    }

    override fun replace(index: T, uri: Uri) {
        imageMap[index] = uri
    }
}
