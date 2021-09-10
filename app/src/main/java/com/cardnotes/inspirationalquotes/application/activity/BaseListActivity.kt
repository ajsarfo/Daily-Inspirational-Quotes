package com.cardnotes.inspirationalquotes.application.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appodeal.ads.Appodeal
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.SHOW_GRADIENT_BACKGROUND
import com.cardnotes.inspirationalquotes.application.binding.ToolbarBinding
import com.cardnotes.inspirationalquotes.application.cache.GradientCache
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.application.cache.PictureCache
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.application.manger.InterstitialManager
import com.cardnotes.inspirationalquotes.application.tools.LoadingDialog
import com.cardnotes.inspirationalquotes.databinding.ActivityListBinding
import com.cardnotes.inspirationalquotes.readSettings
import com.cardnotes.inspirationalquotes.viewmodel.BaseListInterface
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

abstract class BaseListActivity : AppBarsBaseActivity() {

    protected val binding by lazy {
        ActivityListBinding.inflate(LayoutInflater.from(this))
    }

    private val loadingDialog by lazy {
        LoadingDialog(this)
    }

    protected abstract val viewModel: BaseListInterface

    protected abstract suspend fun configureAdapter(
        recyclerView: RecyclerView,
        imageCache: ImageCache<Int>
    )

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(3, 2, 4, 3)
        )
    }

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Appodeal.setBannerViewId(R.id.main_banner)
        viewModel.setBundle(intent.extras)
        loadingDialog.show()
        lifecycleScope.launchWhenCreated {
            val imageCache: ImageCache<Int> = if(readSettings(SHOW_GRADIENT_BACKGROUND, false).first()){
                GradientCache(dependency.imageStore())
            }
            else {
                PictureCache(dependency.imageStore())
            }
            configureToolbar()
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(this@BaseListActivity)
                setHasFixedSize(true)
                configureAdapter(this, imageCache)
            }
        }

        lifecycleScope.launchWhenStarted {
            delay(1000)
            loadingDialog.dismiss()
        }
    }

    private fun configureToolbar() {
        binding.toolbar.let {
            it.binding = ToolbarBinding(dependency) { onBackPressed() }.apply {
                title = viewModel.toolbarTitle()
            }
            it.executePendingBindings()
        }
    }

    override fun navigate(destination: Destination) {
       interstitialManager.showAd {
           startActivity(Intent(this, QuoteActivity::class.java))
           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
       }
    }
}