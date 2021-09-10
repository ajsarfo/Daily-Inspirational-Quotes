package com.cardnotes.inspirationalquotes.application.activity

import androidx.activity.viewModels
import com.cardnotes.inspirationalquotes.viewmodel.PagingBlockViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingBlockActivity : BasePagingActivity() {

    override val viewModel by viewModels<PagingBlockViewModel>()
}