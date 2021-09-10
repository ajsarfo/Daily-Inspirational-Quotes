package com.cardnotes.inspirationalquotes.application.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.cardnotes.inspirationalquotes.REMAINDER_TIMER
import com.cardnotes.inspirationalquotes.SHOW_NOTIFICATIONS
import com.cardnotes.inspirationalquotes.application.reciever.AlarmReceiver
import com.cardnotes.inspirationalquotes.readSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AlarmMaker @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val alarmManager: AlarmManager? by lazy {
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun getPendingIntent(): PendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, AlarmReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun estimateOffsetTime(notifyMinutes: Long): Long {
        val dailyMinutes = TimeUnit.HOURS.toMinutes(24)
        val calender = Calendar.getInstance()
        val totalMinutes =
            calender.get(Calendar.HOUR_OF_DAY) * 60 + calender.get(Calendar.MINUTE)
        val minutes =
            if (totalMinutes > notifyMinutes) dailyMinutes - totalMinutes + notifyMinutes
            else notifyMinutes - totalMinutes
        return Calendar.getInstance().timeInMillis + TimeUnit.MINUTES.toMillis(minutes)
    }

    suspend fun startAlarm() {
        if (!context.readSettings(SHOW_NOTIFICATIONS, true).first()) return
        val minutes = context.readSettings(REMAINDER_TIMER, 60 * 8).first()
        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            estimateOffsetTime(minutes.toLong()),
            AlarmManager.INTERVAL_DAY,
            getPendingIntent()
        )
    }

    fun stopAlarm() {
        alarmManager?.cancel(getPendingIntent())
    }
}