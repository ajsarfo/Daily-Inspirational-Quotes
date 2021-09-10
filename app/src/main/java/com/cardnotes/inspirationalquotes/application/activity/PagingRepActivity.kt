package com.cardnotes.inspirationalquotes.application.activity

import androidx.activity.viewModels
import com.cardnotes.inspirationalquotes.viewmodel.PagingRepViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingRepActivity : BasePagingActivity() {

    override val viewModel by viewModels<PagingRepViewModel>()
}