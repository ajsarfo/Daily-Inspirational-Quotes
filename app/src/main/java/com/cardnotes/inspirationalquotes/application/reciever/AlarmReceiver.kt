package com.cardnotes.inspirationalquotes.application.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cardnotes.inspirationalquotes.application.notification.NotificationMaker
import com.cardnotes.inspirationalquotes.data.repository.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var notificationMaker: NotificationMaker

    override fun onReceive(context: Context, intent: Intent) {
        runBlocking {
            repository.database()
                .categoryQuoteDao()
                .random(50).filter { it.message.length in 100..250 }
                .randomOrNull()
                ?.let { notificationMaker.notify(it) }
        }
    }
}
