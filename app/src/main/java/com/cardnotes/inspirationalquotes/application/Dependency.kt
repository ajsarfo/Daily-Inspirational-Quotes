package com.cardnotes.inspirationalquotes.application

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.lifecycleScope
import com.cardnotes.inspirationalquotes.SHOULD_VIBRATE
import com.cardnotes.inspirationalquotes.application.activity.AppBarsBaseActivity
import com.cardnotes.inspirationalquotes.application.file.vibrate
import com.cardnotes.inspirationalquotes.application.image.BitmapImageLoader
import com.cardnotes.inspirationalquotes.application.image.ImageStore
import com.cardnotes.inspirationalquotes.application.listener.NavigationListener
import com.cardnotes.inspirationalquotes.readSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class Dependency(private val activityAppBars: AppBarsBaseActivity) {

    fun loadImageAsync(uri: Uri) : StateFlow<Bitmap?> {
        return activityAppBars.imageLoader.loadImageAsync(uri)
    }

    fun navigationListener() : NavigationListener = activityAppBars

    fun coroutineScope() : CoroutineScope = activityAppBars.lifecycleScope

    fun context() : Context = activityAppBars

    fun customVibrate() {
        activityAppBars.lifecycleScope.launch {
           context().let {
               if(it.readSettings(SHOULD_VIBRATE, true).first()) it.vibrate()
           }
        }
    }

    fun imageStore() : ImageStore = activityAppBars.imageStore

    fun imageLoader() : BitmapImageLoader = activityAppBars.imageLoader
}