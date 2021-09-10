package com.cardnotes.inspirationalquotes.application.adapter

import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.binding.ListItemBinding
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.databinding.LayoutListItemBinding
import com.cardnotes.inspirationalquotes.viewmodel.BaseListInterface

class ListItemViewHolder(
    private val dependency: Dependency,
    private val viewModelBase: BaseListInterface,
    private val imageCache: ImageCache<Int>,
    private val authorVisible: Int,
    private val binding: LayoutListItemBinding
) : AbstractListItemViewHolder(binding.root) {

    override fun bind(position: Int, quote: Quote) {
        binding.binding = ListItemBinding(
            viewModelBase,
            imageCache,
            position,
            authorVisible,
            dependency,
            quote
        ).also { it.init() }
        binding.executePendingBindings()
    }
}