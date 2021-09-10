package com.cardnotes.inspirationalquotes.application.binding

import android.graphics.Bitmap
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cardnotes.inspirationalquotes.BR
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.application.enums.Selection
import com.cardnotes.inspirationalquotes.application.file.bindable
import com.cardnotes.inspirationalquotes.application.image.ImageHolder
import com.cardnotes.inspirationalquotes.application.listener.NavigationListener
import com.cardnotes.inspirationalquotes.viewmodel.MainViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SpecialBinding(
    private val viewModel: MainViewModel,
    private val dependency: Dependency,
    private val navigationListener: NavigationListener
) : BaseObservable() {

    @get:Bindable
    var popular: ImageHolder by bindable(ImageHolder.Empty, BR.popular)

    @get:Bindable
    var proverb: ImageHolder by bindable(ImageHolder.Empty, BR.proverb)

    @get:Bindable
    var love: ImageHolder by bindable(ImageHolder.Empty, BR.love)

    @get:Bindable
    var favorite: ImageHolder by bindable(ImageHolder.Empty, BR.favorite)

    fun init() {
       loadIcon("medal.png") {
           popular = ImageHolder.ImageBitmap(it)
       }
        loadIcon("proverb.png") {
            proverb = ImageHolder.ImageBitmap(it)
        }
        loadIcon("love.png") {
            love = ImageHolder.ImageBitmap(it)
        }
        loadIcon("star.png") {
            favorite = ImageHolder.ImageBitmap(it)
        }
    }

    private fun loadIcon(name: String, onFinished: (Bitmap) -> Unit) {
        dependency.apply {
            coroutineScope().launch {
                loadImageAsync(imageStore().iconImage(name)).collect { bitmap ->
                    bitmap?.let {
                        onFinished(bitmap)
                        throw CancellationException()
                    }
                }
            }
        }
    }

    fun onFavorite() {
        dependency.customVibrate()
        viewModel.container.selection = Selection.FAVORITE
        navigationListener.navigate(Destination.FAVORITE)
    }

    fun onLove() {
        dependency.customVibrate()
        viewModel.container.selection = Selection.LOVE
        navigationListener.navigate(Destination.REP)
    }

    fun onProverbs() {
        dependency.customVibrate()
        viewModel.container.selection = Selection.PROVERB
        navigationListener.navigate(Destination.REP)
    }

    fun onPopular() {
        dependency.customVibrate()
        viewModel.container.selection = Selection.POPULAR
        navigationListener.navigate(Destination.SINGLE)
    }
}