package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.ApplicationScope
import com.cardnotes.inspirationalquotes.application.binding.*
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.application.enums.Selection
import com.cardnotes.inspirationalquotes.application.file.moreApps
import com.cardnotes.inspirationalquotes.application.file.rateApp
import com.cardnotes.inspirationalquotes.application.file.share
import com.cardnotes.inspirationalquotes.application.file.toast
import com.cardnotes.inspirationalquotes.application.manger.AdCountManager
import com.cardnotes.inspirationalquotes.application.manger.AppReviewManager
import com.cardnotes.inspirationalquotes.application.manger.BannerManager
import com.cardnotes.inspirationalquotes.application.tools.Colors
import com.cardnotes.inspirationalquotes.application.tools.LoadingDialog
import com.cardnotes.inspirationalquotes.data.application.QuoteRep
import com.cardnotes.inspirationalquotes.data.database.entity.Author
import com.cardnotes.inspirationalquotes.data.database.entity.Category
import com.cardnotes.inspirationalquotes.databinding.ActivityMainBinding
import com.cardnotes.inspirationalquotes.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppBarsBaseActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    private val viewModel by viewModels<MainViewModel>()

    private val mainPagerBinding by lazy {
        MainPagerBinding(dependency)
    }

    private val reviewManager by lazy {
        AppReviewManager(this).also { it.init() }
    }

    private val loadingDialog by lazy {
        LoadingDialog(this)
    }

    private var exitApp = false

    override fun createAdCounterManager(): AdCountManager {
        return AdCountManager(listOf(1, 3, 4, 3))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_main),
            binding.mainBanner
        )
        /**********************************************************/
        loadingDialog.show()
        //Trigger app review on launch
        lifecycleScope.launchWhenCreated {
            reviewManager.triggerReview()
        }

        configureNavigationDrawer()
        configureToolbar()
        //Creating pager
        viewModel.pageItem.observe(this@MainActivity) {
            mainPagerBinding.setPagerItem(it)
        }
        //Creating all blocks
        viewModel.mainUI.observe(this@MainActivity) {
            setupAuthorBlock(it.authorBlock)
            setupCategoryBlock(it.categoryBlock)
            setupTopBlock(it.topBlock)
            lifecycleScope.launch {
                delay(1000)
                loadingDialog.dismiss()
            }
        }
        //Block Headings
        setupPagerSpecial()
        setupAuthorHeading()
        setupCategoryHeading()
        setupTopHeading()
    }

    private fun setupPagerSpecial() {
        binding.apply {
            pager.binding = mainPagerBinding
            special.binding = SpecialBinding(
                viewModel, dependency, this@MainActivity
            ).also { it.init() }
            pager.executePendingBindings()
            special.executePendingBindings()
        }
    }

    private fun setupCategoryBlock(list: List<Category>) {
        binding.apply {
            categoryBlock.blockBinding = MainBlockBinding(
                dependency,
                list,
                Colors.getInstance().getPallet(),
                container
            )
            categoryBlock.executePendingBindings()
        }
    }

    private fun setupAuthorBlock(list: List<Author>) {
        binding.apply {
            authorBlock.blockBinding = MainBlockBinding(
                dependency,
                list,
                Colors.getInstance().getPallet(),
                container
            )
            authorBlock.executePendingBindings()
        }
    }

    private fun setupTopBlock(list: List<QuoteRep>) {
        binding.apply {
            topBlock.blockBinding = MainBlockBinding(
                dependency,
                list,
                Colors.getInstance().getPallet(),
                container,
                false
            )
            topBlock.executePendingBindings()
        }
    }

    private fun setupAuthorHeading() {
        binding.apply {
            authorHeader.binding = MainHeadingBinding(
                dependency = dependency,
                heading = "Quote by Author"
            ) {
                lifecycleScope.launch { dependency.customVibrate() }
                viewModel.container.selection = Selection.AUTHOR
                navigate(Destination.REP)
            }
            authorHeader.executePendingBindings()
        }
    }

    private fun setupCategoryHeading() {
        binding.apply {
            categoryHeader.binding = MainHeadingBinding(
                dependency = dependency,
                heading = "Quote by Category"
            ) {
                lifecycleScope.launch { dependency.customVibrate() }
                viewModel.container.selection = Selection.CATEGORY
                navigate(Destination.REP)
            }
            categoryHeader.executePendingBindings()
        }
    }

    private fun callExit() {
        if (exitApp) finish()
        else {
            exitApp = true
            toast("Press BACK once again to exit!")
            lifecycleScope.launch {
                delay(2000L)
                exitApp = false
            }
        }
    }

    private fun setupTopHeading() {
        binding.apply {
            topHeader.binding = MainHeadingBinding(
                dependency = dependency,
                heading = "Top Quotes",
                visibility = View.GONE
            ) {}
            binding.executePendingBindings()
        }
    }

    private fun configureNavigationDrawer() {
        binding.navigationView.itemIconTintList = null
        binding.navigationView.setNavigationItemSelectedListener {
            dependency.customVibrate()
            when (it.itemId) {
                R.id.thanksgiving -> {
                    viewModel.container.selection = Selection.THANKSGIVING
                    navigate(Destination.SINGLE)
                    true
                }
                R.id.new_year -> {
                    viewModel.container.selection = Selection.NEW_YEAR
                    navigate(Destination.SINGLE)
                    true
                }
                R.id.wedding_wishes -> {
                    viewModel.container.selection = Selection.WEDDING_WISHES
                    navigate(Destination.SINGLE)
                    true
                }
                R.id.settings -> {
                    navigate(Destination.SETTINGS)
                    true
                }
                R.id.share -> {
                    share(
                        "${getString(R.string.app_share_message)}\n\nhttps://play.google.com/store/apps/details?id=${packageName}",
                        "Share"
                    )
                    true
                }
                R.id.rate -> {
                    rateApp()
                    true
                }
                R.id.more_apps -> {
                    moreApps()
                    true
                }
                else -> false
            }
        }
    }

    private fun configureToolbar() {
        binding.toolbar.also {
            it.binding = ToolbarBinding(icon = R.drawable.ic_menu) {
                if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
            }.apply {
                title = getString(R.string.app_name)
            }
            it.executePendingBindings()
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else callExit()
    }

    override fun navigate(destination: Destination) {
        when (destination) {
            Destination.LIST -> interstitialManager?.showAd {
                navigateToDestination(PagingBlockActivity::class.java, getQuoteRepBundle())
            }
            Destination.SINGLE -> interstitialManager?.showAd {
                navigateToDestination(PagingSingleActivity::class.java, getSingleBundle())
            }
            Destination.REP -> interstitialManager?.showAd {
                navigateToDestination(RepActivity::class.java, getRepListBundle())
            }
            Destination.FAVORITE -> {
                navigateToDestination(FavoriteActivity::class.java, null)
            }
            else -> navigateToDestination(SettingsActivity::class.java, null)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            ApplicationScope.getInstance(repository).execute()
        }
        container.reset()
    }

    override fun onDestroy() {
        super.onDestroy()
        imageLoader.destroy()
    }
}