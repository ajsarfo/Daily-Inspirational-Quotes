package com.cardnotes.inspirationalquotes.application.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cardnotes.inspirationalquotes.data.application.Quote

abstract class AbstractListItemViewHolder(
    private val itemView: View
) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(position: Int, quote: Quote)
}