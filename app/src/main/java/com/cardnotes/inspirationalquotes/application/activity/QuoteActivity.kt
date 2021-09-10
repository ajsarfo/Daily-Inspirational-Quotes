package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.appodeal.ads.Appodeal
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.SHOW_GRADIENT_BACKGROUND
import com.cardnotes.inspirationalquotes.application.binding.QuoteBinding
import com.cardnotes.inspirationalquotes.application.cache.GradientCache
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.application.cache.PictureCache
import com.cardnotes.inspirationalquotes.databinding.ActivityQuoteBinding
import com.cardnotes.inspirationalquotes.readSettings
import com.cardnotes.inspirationalquotes.viewmodel.QuoteViewModel
import kotlinx.coroutines.flow.first

class QuoteActivity : AppBarsBaseActivity() {

    private val binding by lazy {
        ActivityQuoteBinding.inflate(LayoutInflater.from(this))
    }

    private val viewModel by viewModels<QuoteViewModel>()

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Appodeal.setBannerViewId(R.id.main_banner)
        setContentView(binding.root)
        viewModel.fetchQuote()
        lifecycleScope.launchWhenCreated {
            val imageCache: ImageCache<Int> = if(readSettings(SHOW_GRADIENT_BACKGROUND, false).first()){
                GradientCache(dependency.imageStore())
            }
            else {
                PictureCache(dependency.imageStore())
            }
            viewModel.quote.observe(this@QuoteActivity) { quote ->
                binding.binding = QuoteBinding(viewModel, imageCache, dependency, quote).also {
                    it.init()
                }
                binding.executePendingBindings()
            }
        }
    }
}