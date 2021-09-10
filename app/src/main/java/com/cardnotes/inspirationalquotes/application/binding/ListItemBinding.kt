package com.cardnotes.inspirationalquotes.application.binding

import android.view.View
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.viewmodel.BaseListInterface

class ListItemBinding(
    private val viewModelBase: BaseListInterface,
    private val listImageCache: ImageCache<Int>,
    private val position: Int,
    visible: Int,
    dependency: Dependency,
    quote: Quote
) : BaseBinding(dependency, quote, visible == View.VISIBLE) {

    fun init() {
        setFavoriteIcon()
        setImage(listImageCache.get(position))
    }

    override fun save() {
        viewModelBase.saveQuote(quote)
    }

    override fun onChangeImage() {
        dependency.customVibrate()
        setImage(
            listImageCache.random().also {
                listImageCache.replace(position, it)
            }
        )
    }

    fun onClick() {
        dependency.customVibrate()
        viewModelBase.container().apply {
            selectedQuote = quote
            selectedQuoteImageUri = listImageCache.get(position)
            saveImage = {
                listImageCache.replace(position, it)
                setImage(it)
            }
            saveFavorite = {
                quote.favorite = it
                setFavoriteIcon()
            }
        }
        dependency.navigationListener().navigate(Destination.QUOTE)
    }
}