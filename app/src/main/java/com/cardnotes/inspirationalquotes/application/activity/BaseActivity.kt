package com.cardnotes.inspirationalquotes.application.activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.application.ContainerExtra
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.application.enums.Selection
import com.cardnotes.inspirationalquotes.application.image.BitmapImageLoader
import com.cardnotes.inspirationalquotes.application.image.ImageStore
import com.cardnotes.inspirationalquotes.application.listener.NavigationListener
import com.cardnotes.inspirationalquotes.application.listener.StatusNavigationListener
import com.cardnotes.inspirationalquotes.application.manger.NetworkManager
import com.cardnotes.inspirationalquotes.data.database.entity.Author
import com.cardnotes.inspirationalquotes.data.database.entity.Category
import com.cardnotes.inspirationalquotes.data.database.entity.Love
import com.cardnotes.inspirationalquotes.data.database.entity.Proverb
import com.cardnotes.inspirationalquotes.data.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), StatusNavigationListener, NavigationListener {

    //These are been used by dependency
    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var imageStore: ImageStore

    @Inject
    lateinit var imageLoader: BitmapImageLoader

    @Inject
    lateinit var container: Container
    /////////////////////////////////////

    protected fun removeStatusBarBackgroundLight() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
        }
    }

    protected fun removeNavigationBackgroundLight() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            }
        }
    }

    private fun changeStatusBackground() {
        when (resources.configuration.uiMode and (Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
            }
            Configuration.UI_MODE_NIGHT_NO -> setStatusBarBackgroundLight()
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
            }
        }
    }

    private fun setStatusBarBackgroundLight() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setNavigationBarBackgroundLight() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                window.decorView.windowInsetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                )
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun navigate(destination: Destination) {}


    override fun statusColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = color
                if (color == Color.WHITE) setStatusBarBackgroundLight()
            }
        }
    }


    override fun navigationColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                navigationBarColor = color
                if (color == Color.WHITE) setNavigationBarBackgroundLight()
            }
        }
    }

    protected fun <T> navigateToDestination(klass: Class<T>, bundle: Bundle?) {
        val intent = Intent(this, klass).apply {
            bundle?.let {
                putExtras(it)
            }
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun getSingleBundle() : Bundle? {
        return container.selection?.let { selection ->
            Bundle().apply {
                when (selection) {
                    Selection.POPULAR -> {
                        putString(
                            ContainerExtra.SELECTED_SINGLE_TYPE,
                            ContainerExtra.SELECTED_SINGLE_TYPE_POPULAR
                        )
                    }
                    Selection.THANKSGIVING -> {
                        putString(
                            ContainerExtra.SELECTED_SINGLE_TYPE,
                            ContainerExtra.SELECTED_SINGLE_TYPE_THANKSGIVING
                        )
                    }
                    Selection.WEDDING_WISHES -> {
                        putString(
                            ContainerExtra.SELECTED_SINGLE_TYPE,
                            ContainerExtra.SELECTED_SINGLE_TYPE_WEDDING_WISHES
                        )
                    }
                    Selection.NEW_YEAR -> {
                        putString(
                            ContainerExtra.SELECTED_SINGLE_TYPE,
                            ContainerExtra.SELECTED_SINGLE_TYPE_NEW_YEAR
                        )
                    }
                    else -> { }
                }
            }
        }
    }

    fun getRepListBundle() : Bundle? {
        return container.selection?.let { selection ->
            Bundle().apply {
                when(selection) {
                    Selection.LOVE -> {
                        putInt(
                            ContainerExtra.SELECTED_REP_LIST_TYPE,
                            ContainerExtra.SELECTED_REP_LIST_LOVE
                        )
                    }
                    Selection.PROVERB -> {
                        putInt(
                            ContainerExtra.SELECTED_REP_LIST_TYPE,
                            ContainerExtra.SELECTED_REP_LIST_PROVERB
                        )
                    }
                    Selection.AUTHOR -> {
                        putInt(
                            ContainerExtra.SELECTED_REP_LIST_TYPE,
                            ContainerExtra.SELECTED_REP_LIST_AUTHOR
                        )
                    }
                    Selection.CATEGORY -> {
                        putInt(
                            ContainerExtra.SELECTED_REP_LIST_TYPE,
                            ContainerExtra.SELECTED_REP_LIST_CATEGORY
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun getQuoteRepBundle() : Bundle? {
        return container.selectedQuoteRep?.let { quoteRep ->
            Bundle().apply {
                putString(ContainerExtra.SELECTED_QUOTE_REP, quoteRep.rep)
                when (quoteRep) {
                    is Author -> putInt(
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE,
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_AUTHOR
                    )
                    is Category -> putInt(
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE,
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_CATEGORY
                    )
                    is Love -> putInt(
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE,
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_LOVE
                    )
                    is Proverb -> putInt(
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE,
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_PROVERB
                    )
                }
            }
        }
    }
}