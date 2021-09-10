package com.cardnotes.inspirationalquotes.application.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BitmapImageLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val imageLoader = ImageLoader(context)
    private val imageLoadingScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private suspend fun buildAndLoad(uri: Uri): Bitmap {
        return imageLoader.execute(
            ImageRequest.Builder(context)
                .data(uri)
                .build()
        ).drawable.let { (it as BitmapDrawable).bitmap }
    }

    fun loadImageAsync(uri: Uri): StateFlow<Bitmap?> {
        val imageFlow = MutableStateFlow<Bitmap?>(null)
        imageLoadingScope.launch {
            imageFlow.value = buildAndLoad(uri)
        }
        return imageFlow
    }

    fun destroy() {
        imageLoadingScope.coroutineContext.cancelChildren()
    }
}