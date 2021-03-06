package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.adapter.RepItemAdapter
import com.cardnotes.inspirationalquotes.application.binding.ToolbarBinding
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.application.manger.AdCountManager
import com.cardnotes.inspirationalquotes.application.manger.BannerManager
import com.cardnotes.inspirationalquotes.databinding.ActivityRepBinding
import com.cardnotes.inspirationalquotes.viewmodel.RepViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepActivity : AppBarsBaseActivity() {

    private val binding by lazy {
        ActivityRepBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val repAdapter by lazy {
        RepItemAdapter(dependency, viewModel)
    }

    private val viewModel by viewModels<RepViewModel>()

    override fun createAdCounterManager(): AdCountManager {
        return AdCountManager(listOf(1, 3, 4, 3))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_rep),
            binding.mainBanner
        )
        /**********************************************************/
        with(viewModel) {
            setBundle(intent.extras)
            init()
        }
        binding.toolbar.let {
            it.binding = ToolbarBinding(dependency) { onBackPressed() }.apply {
                title = viewModel.toolbarTitle
            }
            it.executePendingBindings()
        }

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RepActivity)
            adapter = repAdapter
        }

        viewModel.fetchQuoteReps()
        viewModel.quoteRep.observe(this) {
            repAdapter.submitData(it)
        }
    }

    override fun navigate(destination: Destination) {
       interstitialManager?.showAd {
           navigateToDestination(PagingRepActivity::class.java, getQuoteRepBundle())
       }
    }
}