package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.SHOW_AUTHOR
import com.cardnotes.inspirationalquotes.application.adapter.ListItemAdapter
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.readSettings
import com.cardnotes.inspirationalquotes.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class FavoriteActivity : BaseListActivity() {

    private lateinit var listAdapter: ListItemAdapter

    override val viewModel by viewModels<FavoriteViewModel>()

    override suspend fun configureAdapter(recyclerView: RecyclerView, imageCache: ImageCache<Int>) {
        recyclerView.adapter = ListItemAdapter(
            dependency,
            viewModel,
            imageCache,
            if (readSettings(SHOW_AUTHOR, true).first()) View.VISIBLE else View.GONE
        ).also {
            listAdapter = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.quoteList.observe(this@FavoriteActivity) {
                listAdapter.submitData(it)
            }
        }
    }
}