package com.cardnotes.inspirationalquotes.application.adapter

import androidx.recyclerview.widget.RecyclerView
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.binding.RepItemBinding
import com.cardnotes.inspirationalquotes.data.application.QuoteRep
import com.cardnotes.inspirationalquotes.databinding.LayoutRepItemBinding
import com.cardnotes.inspirationalquotes.viewmodel.RepViewModel

class RepItemViewHolder(
    private val dependency: Dependency,
    private val viewModel: RepViewModel,
    private val repCache: RepItemAdapter.RepCache,
    private val binding: LayoutRepItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(position: Int, rep: QuoteRep) {
        binding.binding = RepItemBinding(
            dependency,
            viewModel,
            repCache,
            position,
            rep
        )
        binding.executePendingBindings()
    }
}