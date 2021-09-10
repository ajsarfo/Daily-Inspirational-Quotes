package com.cardnotes.inspirationalquotes.application.activity

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.cardnotes.inspirationalquotes.application.Dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class AppBarsBaseActivity : BaseActivity() {

    protected val dependency by lazy {
        Dependency(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            when(resources.configuration.uiMode and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {  navigationColor(Color.BLACK) }
                Configuration.UI_MODE_NIGHT_NO -> { navigationColor(Color.WHITE) }
                Configuration.UI_MODE_NIGHT_UNDEFINED -> { }
            }
        }
    }
}