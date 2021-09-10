package com.cardnotes.inspirationalquotes.application.binding

import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.data.application.QuoteRep

class MainCardBinding(
    private val dependency: Dependency,
    private val rep: QuoteRep,
    val color: Int,
    val onClick: (QuoteRep) -> Unit
) {
    fun content() : String = "${rep.rep}\nQuotes"

    fun clicked() {
        dependency.customVibrate()
        onClick(rep)
    }
}