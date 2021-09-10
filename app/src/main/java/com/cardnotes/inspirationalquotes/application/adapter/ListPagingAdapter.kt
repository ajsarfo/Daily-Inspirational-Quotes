package com.cardnotes.inspirationalquotes.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.databinding.LayoutListItemBinding
import com.cardnotes.inspirationalquotes.databinding.LayoutSeparatorBinding
import com.cardnotes.inspirationalquotes.viewmodel.PagingBaseViewModel

class ListPagingAdapter(
    private val dependency: Dependency,
    private val baseViewModel: PagingBaseViewModel,
    private val imageCache: ImageCache<Int>,
    private val authorVisible: Int,
) : PagingDataAdapter<Quote, AbstractListItemViewHolder>(QuoteComparator) {

    override fun onBindViewHolder(holder: AbstractListItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(position, it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractListItemViewHolder {
        return if (viewType == LIST_QUOTE) {
            val binding = LayoutListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ListItemViewHolder(dependency, baseViewModel, imageCache, authorVisible, binding)
        } else {
            SeparatorItemViewHolder(
                LayoutSeparatorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Quote.Separator) QUOTE_SEPARATOR else LIST_QUOTE
    }

    companion object {
        const val QUOTE_SEPARATOR = 0
        const val LIST_QUOTE = 1
    }

    object QuoteComparator : DiffUtil.ItemCallback<Quote>() {

        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return if (
                oldItem is Quote.Separator && newItem !is Quote.Separator
                || newItem is Quote.Separator && oldItem !is Quote.Separator
            ) false
            else if (oldItem is Quote.Separator && newItem is Quote.Separator)
                oldItem.name == newItem.name
            else oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            if (oldItem is Quote.Separator && newItem is Quote.Separator)
                return oldItem.name == newItem.name
            return oldItem.id == newItem.id
        }
    }
}