package com.cardnotes.inspirationalquotes.application.activity

import androidx.activity.viewModels
import com.cardnotes.inspirationalquotes.viewmodel.PagingSingleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingSingleActivity : BasePagingActivity() {

    override val viewModel by viewModels<PagingSingleViewModel>()
}