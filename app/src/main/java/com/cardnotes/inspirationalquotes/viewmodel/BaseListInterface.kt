package com.cardnotes.inspirationalquotes.viewmodel

import android.os.Bundle
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.data.application.Quote

interface BaseListInterface {
    fun container() : Container
    fun saveQuote(quote: Quote)
    fun setBundle(bundle: Bundle?)
    fun toolbarTitle() : String?
}