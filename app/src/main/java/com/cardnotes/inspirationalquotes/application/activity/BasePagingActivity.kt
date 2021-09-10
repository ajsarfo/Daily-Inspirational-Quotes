package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.cardnotes.inspirationalquotes.SHOW_AUTHOR
import com.cardnotes.inspirationalquotes.application.adapter.ListPagingAdapter
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.readSettings
import com.cardnotes.inspirationalquotes.viewmodel.PagingBaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

abstract class BasePagingActivity : BaseListActivity() {

    abstract override val viewModel: PagingBaseViewModel

    private lateinit var listAdapter: ListPagingAdapter

    override suspend fun configureAdapter(
        recyclerView: RecyclerView,
        imageCache: ImageCache<Int>
    ) {
        recyclerView.adapter = ListPagingAdapter(
            dependency,
            viewModel,
            imageCache,
            if (readSettings(SHOW_AUTHOR, true).first()) View.VISIBLE
            else View.GONE
        ).also {
            listAdapter = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.quoteFlow?.collect {
                listAdapter.submitData(it)
            }
        }
    }
}