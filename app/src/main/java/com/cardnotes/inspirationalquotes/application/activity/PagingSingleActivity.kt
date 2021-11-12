package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.manger.BannerManager
import com.cardnotes.inspirationalquotes.viewmodel.PagingSingleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingSingleActivity : BasePagingActivity() {

    override val viewModel by viewModels<PagingSingleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_paging_single),
            binding.mainBanner
        )
        /**********************************************************/
    }
}