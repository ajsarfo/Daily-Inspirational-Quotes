package com.cardnotes.inspirationalquotes.application.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.android.material.timepicker.MaterialTimePicker
import com.cardnotes.inspirationalquotes.*
import com.cardnotes.inspirationalquotes.application.notification.AlarmMaker
import com.cardnotes.inspirationalquotes.databinding.ActivitySettingsBinding
import com.cardnotes.inspirationalquotes.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppBarsBaseActivity() {

    @Inject
    lateinit var alarmMaker: AlarmMaker

    private val binding by lazy {
        ActivitySettingsBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureToolbar()
        lifecycleScope.launchWhenCreated {
            configureVibrate()
            configureAuthorName()
            configureGradientBackground()
            configureNightMode()
            configureEnableNotification()
            configureSetRemainderTime()
        }
    }

    private fun configureToolbar() {
        binding.backArrow.setOnClickListener {
            dependency.customVibrate()
            onBackPressed()
        }
    }

    private suspend fun configureVibrate() {
        binding.vibrate.apply {
            isChecked = readSettings(SHOULD_VIBRATE, true).first()
            setOnClickListener {
                dependency.customVibrate()
                viewModel.scope().launch { editSettings(SHOULD_VIBRATE, isChecked) }
            }
        }
    }

    private suspend fun configureGradientBackground() {
        binding.gradientBackground.apply {
            isChecked = readSettings(SHOW_GRADIENT_BACKGROUND, false).first()
            setOnClickListener {
                dependency.customVibrate()
                viewModel.scope().launch { editSettings(SHOW_GRADIENT_BACKGROUND, isChecked) }
            }
        }
    }

    private suspend fun configureAuthorName() {
        binding.showAuthorName.apply {
            isChecked = readSettings(SHOW_AUTHOR, true).first()
            setOnClickListener {
                dependency.customVibrate()
                viewModel.scope().launch { editSettings(SHOW_AUTHOR, isChecked) }
            }
        }
    }

    private suspend fun configureNightMode() {
        binding.useNightMode.isChecked = readSettings(IS_DARK_MODE, false).first()
        binding.useNightMode.apply {
            setOnClickListener {
                dependency.customVibrate()
                viewModel.scope().launch { editSettings(IS_DARK_MODE, isChecked) }
                if (isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            }
        }
    }

    private suspend fun configureSetRemainderTime() {
        binding.remainderTime.text = convertMinuteToString(
            readSettings(REMAINDER_TIMER, 8 * 60).first()
        )
        binding.remainderLayout.setOnClickListener {
            dependency.customVibrate()
            val picker = Calendar.getInstance().let {
                MaterialTimePicker
                    .Builder()
                    .setHour(it.get(Calendar.HOUR_OF_DAY))
                    .setMinute(it.get(Calendar.MINUTE))
                    .setTitleText("Set Notification Time")
                    .build()
            }

            picker.addOnPositiveButtonClickListener { _ ->
                val totalMinutes = 60 * picker.hour + picker.minute
                viewModel.scope().launch {
                    binding.remainderTime.text = convertMinuteToString(totalMinutes)
                    editSettings(REMAINDER_TIMER, totalMinutes)
                    alarmMaker.startAlarm()
                }
            }
            picker.show(supportFragmentManager, picker.toString())
        }
    }

    private suspend fun configureEnableNotification() {
        binding.enableNotifications.apply {
            isChecked = readSettings(SHOW_NOTIFICATIONS, true).first()
            setOnClickListener {
                dependency.customVibrate()
                viewModel.scope().launch {
                    editSettings(SHOW_NOTIFICATIONS, isChecked)
                    if (isChecked) alarmMaker.startAlarm() else alarmMaker.stopAlarm()
                }
            }
        }
    }

    private fun convertMinuteToString(totalMinutes: Int): String {
        val hours = totalMinutes.div(60)
        val initial = if (hours >= 12) "PM" else "AM"
        val minutes = totalMinutes.rem(60)
        return "${hours.rem(12)} : ${String.format("%02d", minutes)} $initial"
    }
}