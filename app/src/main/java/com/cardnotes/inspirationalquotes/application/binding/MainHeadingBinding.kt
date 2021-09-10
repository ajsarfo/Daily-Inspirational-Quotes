package com.cardnotes.inspirationalquotes.application.binding

import android.view.View
import com.cardnotes.inspirationalquotes.application.Dependency

class MainHeadingBinding(
    val dependency: Dependency,
    val visibility: Int = View.VISIBLE,
    val heading: String,
    private val onClick: () -> Unit
) {
    fun onClick() {
        dependency.customVibrate()
        onClick.invoke()
    }
}