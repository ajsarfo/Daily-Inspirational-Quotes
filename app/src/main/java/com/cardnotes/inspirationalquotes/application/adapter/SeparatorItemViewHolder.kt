package com.cardnotes.inspirationalquotes.application.adapter

import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.databinding.LayoutSeparatorBinding

class SeparatorItemViewHolder(
    private val binding: LayoutSeparatorBinding
) : AbstractListItemViewHolder(binding.root) {

    override fun bind(position: Int, quote: Quote) {}
}