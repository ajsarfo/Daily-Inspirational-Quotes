package com.cardnotes.inspirationalquotes.application.binding

import android.net.Uri
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cardnotes.inspirationalquotes.BR
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.file.bindable
import com.cardnotes.inspirationalquotes.application.image.ImageHolder
import com.cardnotes.inspirationalquotes.viewmodel.PageItem
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainPagerBinding(private val dependency: Dependency) : BaseObservable() {

    val indicatorSwitcher = IndicatorSwitcher()

    @get:Bindable
    var pagerImage: ImageHolder by bindable(ImageHolder.Empty, BR.pagerImage)

    @get:Bindable
    var pagerQuote by bindable("", BR.pagerQuote)

    private fun loadImageAsync(dependency: Dependency, uri: Uri) {
        dependency.coroutineScope().launch {
            dependency.imageLoader().loadImageAsync(uri).collect { bitmap ->
                bitmap?.let {
                    pagerImage = ImageHolder.ImageBitmap(it)
                    throw CancellationException()
                }
            }
        }
    }

    fun setPagerItem(pagerItem: PageItem) {
        pagerItem.uri?.let { loadImageAsync(dependency, it) }
        pagerItem.message?.let { pagerQuote = it }
        indicatorSwitcher.switch()
    }

    class IndicatorBinding() : BaseObservable() {
        @get:Bindable
        var selected by bindable(View.GONE, BR.selected)

        @get:Bindable
        var unselected by bindable(View.VISIBLE, BR.unselected)

        fun switchVisibility() {
            selected = if (selected == View.VISIBLE) View.GONE else View.VISIBLE
            unselected = if (unselected == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }

    class IndicatorSwitcher() {
        private var firstOffset = 0
        private var secondOffset = -1

        val list: List<IndicatorBinding> = listOf(
            IndicatorBinding(),
            IndicatorBinding(),
            IndicatorBinding(),
            IndicatorBinding()
        ).also { it.first().switchVisibility() }

        fun switch() {
            ++firstOffset
            ++secondOffset
            if (firstOffset >= list.size) firstOffset = 0
            if (secondOffset >= list.size) secondOffset = 0
            list[firstOffset].switchVisibility()
            list[secondOffset].switchVisibility()
        }
    }
}