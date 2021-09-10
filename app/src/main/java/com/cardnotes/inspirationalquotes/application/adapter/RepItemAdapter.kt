package com.cardnotes.inspirationalquotes.application.adapter

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.getOrDefault
import androidx.recyclerview.widget.RecyclerView
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.tools.Colors
import com.cardnotes.inspirationalquotes.data.application.QuoteRep
import com.cardnotes.inspirationalquotes.data.database.entity.Author
import com.cardnotes.inspirationalquotes.data.database.entity.Category
import com.cardnotes.inspirationalquotes.data.database.entity.Love
import com.cardnotes.inspirationalquotes.data.database.entity.Proverb
import com.cardnotes.inspirationalquotes.databinding.LayoutRepItemBinding
import com.cardnotes.inspirationalquotes.viewmodel.RepViewModel
import java.util.*

class RepItemAdapter(
    private val dependency: Dependency,
    private val viewModel: RepViewModel,
    private var items: List<QuoteRep> = emptyList()
) : RecyclerView.Adapter<RepItemViewHolder>() {

    private val repCache = RepCache()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepItemViewHolder {
         val binding = LayoutRepItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepItemViewHolder(dependency, viewModel, repCache, binding)
    }

    override fun onBindViewHolder(holder: RepItemViewHolder, position: Int) {
        holder.bind(position, items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitData(list: List<QuoteRep>) {
        items = list
        notifyDataSetChanged()
    }

    class RepCache {
        private val initialMap = hashMapOf<String, String>()
        private val repColors = SparseIntArray()

        private fun computeInitial(quoteRep: QuoteRep): String {
            val buffer = StringBuffer()
            when (quoteRep is Author || quoteRep is Category) {
                true -> {
                    quoteRep.rep.split(" ").filter { it != "" }.let {
                        if (it.size == 1) buffer.append(it.first().substring(0, 2))
                        else {
                            buffer.append(it.first().first())
                            buffer.append(it.last().first())
                        }
                    }
                }
                else -> {
                    buffer.append(quoteRep.rep.substring(0, 2))
                }
            }
            return buffer.toString().toUpperCase(Locale.ENGLISH)
        }

        fun getInitial(quoteRep: QuoteRep) : String {
            return initialMap.getOrPut(quoteRep.rep) {
                computeInitial(quoteRep)
            }
        }

        fun getColor(position: Int, rep: QuoteRep) : Int {
            var colorId = repColors.getOrDefault(position, -1)
            if (colorId == -1) {
                colorId = Colors.getInstance().run {
                    when (rep) {
                        is Love -> getRandomLoveColorId()
                        is Proverb -> getRandomProverColorId()
                        else -> getRandomColorId()
                    }
                }
                repColors.put(position, colorId)
            }
            return Colors.getInstance().run {
                when (rep) {
                    is Love -> getLoveColor(colorId)
                    is Proverb -> getProverbColor(colorId)
                    else -> getColor(colorId)
                }
            }
        }
    }
}