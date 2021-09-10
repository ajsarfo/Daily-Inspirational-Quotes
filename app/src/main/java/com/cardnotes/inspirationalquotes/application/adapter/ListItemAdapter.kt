package com.cardnotes.inspirationalquotes.application.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.databinding.LayoutListItemBinding
import com.cardnotes.inspirationalquotes.viewmodel.BaseListInterface

class ListItemAdapter(
    private val dependency: Dependency,
    private val viewModel: BaseListInterface,
    private val imageCache: ImageCache<Int>,
    private val authorVisible: Int,
    private var items: MutableList<Quote> = mutableListOf()
) : RecyclerView.Adapter<AbstractListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractListItemViewHolder {
       val binding = LayoutListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListItemViewHolder(dependency, viewModel, imageCache, authorVisible, binding)
    }

    override fun onBindViewHolder(holder: AbstractListItemViewHolder, position: Int) {
        holder.bind(position, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitData(list: List<Quote>) {
        items = list.toMutableList()
        notifyDataSetChanged()
    }
}