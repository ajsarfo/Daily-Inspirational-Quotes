package com.cardnotes.inspirationalquotes.application.binding

import androidx.databinding.BaseObservable
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.adapter.RepItemAdapter
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.data.application.QuoteRep
import com.cardnotes.inspirationalquotes.viewmodel.RepViewModel

class RepItemBinding(
    private val dependency: Dependency,
    private val viewModel: RepViewModel,
    repCache: RepItemAdapter.RepCache,
    position: Int,
    private val quoteRep: QuoteRep
) : BaseObservable() {

    val title = quoteRep.rep
    val subtitle = viewModel.subtitle
    val color = repCache.getColor(position, quoteRep)
    val initial = repCache.getInitial(quoteRep)

    fun onClick() {
        dependency.customVibrate()
        viewModel.setSelected(quoteRep)
        viewModel.isProverb(quoteRep).let {
            val destination = if (it) Destination.PROVERBS else Destination.LOVE
            dependency.navigationListener().navigate(destination)
        }
    }
}