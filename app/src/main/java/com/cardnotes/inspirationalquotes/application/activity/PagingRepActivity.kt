package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.manger.BannerManager
import com.cardnotes.inspirationalquotes.viewmodel.PagingRepViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingRepActivity : BasePagingActivity() {

    override val viewModel by viewModels<PagingRepViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_paging_rep),
            binding.mainBanner
        )
        /**********************************************************/
    }
}