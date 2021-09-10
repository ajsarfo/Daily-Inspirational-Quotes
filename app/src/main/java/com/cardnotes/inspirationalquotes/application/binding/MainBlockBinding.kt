package com.cardnotes.inspirationalquotes.application.binding

import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.application.Dependency
import com.cardnotes.inspirationalquotes.application.enums.Destination
import com.cardnotes.inspirationalquotes.data.application.QuoteRep

class MainBlockBinding(
    private val dependency: Dependency,
    private val repList: List<QuoteRep>,
    private val colorList: List<Int>,
    private val container: Container,
    val isSmall: Boolean = true
) {

    fun getBlock(position: Int) : MainCardBinding {
        return MainCardBinding(dependency,repList[position], colorList[position]) {
            container.selectedQuoteRep = it
            dependency.navigationListener().navigate(Destination.LIST)
        }
    }
}