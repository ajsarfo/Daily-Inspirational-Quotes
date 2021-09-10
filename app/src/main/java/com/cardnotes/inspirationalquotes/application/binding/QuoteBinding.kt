package com.cardnotes.inspirationalquotes.application.binding

import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.viewmodel.QuoteViewModel

class QuoteBinding(
    private val viewModel: QuoteViewModel,
    private val imageCache: ImageCache<Int>,
    dependency: Dependency,
    quote: Quote
) : BaseBinding(dependency, quote, false) {

    fun init() {
        setFavoriteIcon()
        viewModel.container.selectedQuoteImageUri?.let {
            setImage(it)
        }
    }

    override fun save() {
        viewModel.save(quote)
    }

    override fun onFavorite() {
        super.onFavorite()
        viewModel.container.saveFavorite?.invoke(quote.favorite)
    }

    override fun onChangeImage() {
        dependency.customVibrate()
        dependency.apply {
            imageCache.random().let {
                setImage(it)
                viewModel.container.saveImage?.invoke(it)
            }
        }
    }
}