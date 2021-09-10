package com.cardnotes.inspirationalquotes.application.binding

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cardnotes.inspirationalquotes.BR
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.file.*
import com.cardnotes.inspirationalquotes.application.image.ImageHolder
import com.cardnotes.inspirationalquotes.data.application.Quote
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseBinding(
    protected val dependency: Dependency,
    protected val quote: Quote,
    showAuthor: Boolean
) : BaseObservable() {

    @get:Bindable
    var image: ImageHolder by bindable(ImageHolder.Empty, BR.image)

    @get:Bindable
    var favorite: ImageHolder by bindable(ImageHolder.Empty, BR.favorite)

    var message = if(showAuthor) "“${quote.message}”\n\n_${quote.name}" else "“${quote.message}”"

    fun setFavoriteIcon() {
        favorite = if(quote.favorite) ImageHolder.ImageDrawable(R.drawable.star_filled)
        else ImageHolder.ImageDrawable(R.drawable.ic_star_unfilled)
    }

    protected fun setImage(imageUri: Uri) {
        dependency.coroutineScope().launch {
            dependency.imageLoader().loadImageAsync(imageUri).collect { bitmap ->
                bitmap?.let {
                    image = ImageHolder.ImageBitmap(it)
                    throw CancellationException()
                }
            }
        }
    }

    protected abstract fun save()

    abstract fun onChangeImage()

    open fun onFavorite() {
        dependency.customVibrate()
        quote.let {
            it.favorite = !it.favorite
            //Perform save here
            save()
            dependency.context().toast(
                if (it.favorite) "Added to favourites" else "Removed from favorites"
            )
        }
        setFavoriteIcon()
    }

    fun onShare() {
        dependency.customVibrate()
        dependency.context().apply {
            vibrate()
            share("\"${quote.message}\"\n\n_${quote.name}", "Share")
        }
    }

    fun onCopy() {
        dependency.customVibrate()
        dependency.context().apply {
            copy("\"${quote.message}\"\n\n_${quote.name}", "share")
            toast("Copied to clipboard")
        }
    }
}