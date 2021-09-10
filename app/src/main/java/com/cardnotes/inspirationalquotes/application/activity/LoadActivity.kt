package com.cardnotes.inspirationalquotes.application.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.cardnotes.inspirationalquotes.R
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.application.listener.NavigationListener
import com.cardnotes.inspirationalquotes.data.repository.Repository
import com.cardnotes.inspirationalquotes.databinding.LayoutDialogLoadBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoadActivity : AppBarsBaseActivity() {

    private val loadingDialog by lazy {
        LoadingDialog(
            this,
            LayoutDialogLoadBinding.inflate(LayoutInflater.from(this)),
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            loadingDialog.show()
            repository.setupDatabase(loadingDialog)
        }
    }

    override fun navigate(destination: Destination) {
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private class LoadingDialog(
        context: Context,
        private val binding: LayoutDialogLoadBinding,
        private val navigationListener: NavigationListener
    ) : AlertDialog(context), Repository.RepositoryListener {

        private var indicatorIterator = 0

        private val views = binding.run {
            listOf(first, second, third, forth, fifth)
        }

        init {
            setView(binding.root)
            setCancelable(false)
        }

        override suspend fun check() {
            views[indicatorIterator].setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.color_primary)
            )
            indicatorIterator++
            if(indicatorIterator == 5) navigationListener.navigate(Destination.SPLASH)
        }
    }
}