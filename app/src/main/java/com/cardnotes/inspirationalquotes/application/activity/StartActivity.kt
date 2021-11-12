package com.cardnotes.inspirationalquotes.application.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.cardnotes.inspirationalquotes.*
import com.cardnotes.inspirationalquotes.application.enums.Destination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : AppBarsBaseActivity() {

    override fun canShowInterstitial(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {  savedInstanceState ?: configureDarkMode() }
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            editSettings(App_START_UP_TIMES, readSettings(App_START_UP_TIMES, 0).first() + 1)
            if(repository.isDatabaseCreated()) navigate(Destination.SPLASH)
            else navigate(Destination.LOAD)
        }
    }

    private suspend fun configureDarkMode() {
        val isDarkMode = readSettings(IS_DARK_MODE, false).first()
        if(isDarkMode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        recreate()
    }

    override fun navigate(destination: Destination) {
       val intent = when(destination) {
            Destination.LOAD -> Intent(this, LoadActivity::class.java)
           else -> Intent(this, SplashActivity::class.java)
        }
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.no_anim, R.anim.no_anim)
    }
}