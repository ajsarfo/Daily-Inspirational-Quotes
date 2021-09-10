package com.cardnotes.inspirationalquotes.application

import android.net.Uri
import com.cardnotes.inspirationalquotes.application.enums.Selection
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.application.QuoteRep
import javax.inject.Inject
import javax.inject.Singleton

object ContainerExtra {
    const val SELECTED_SINGLE_TYPE ="selected_single_type"
    const val SELECTED_SINGLE_TYPE_POPULAR = "Popular"
    const val SELECTED_SINGLE_TYPE_THANKSGIVING = "Thanksgiving"
    const val SELECTED_SINGLE_TYPE_NEW_YEAR = "New Year"
    const val SELECTED_SINGLE_TYPE_WEDDING_WISHES = "Wedding Wishes"

    const val SELECTED_QUOTE_REP_TYPE = "selected_quote_rep_type"
    const val SELECTED_QUOTE_REP_TYPE_AUTHOR = 0
    const val SELECTED_QUOTE_REP_TYPE_CATEGORY = 1
    const val SELECTED_QUOTE_REP_TYPE_LOVE = 2
    const val SELECTED_QUOTE_REP_TYPE_PROVERB = 3

    const val SELECTED_QUOTE_REP = "selected_quote_rep"

    const val SELECTED_REP_LIST_TYPE = "selected_rep_list_type"
    const val SELECTED_REP_LIST_AUTHOR = 0
    const val SELECTED_REP_LIST_CATEGORY = 1
    const val SELECTED_REP_LIST_PROVERB = 2
    const val SELECTED_REP_LIST_LOVE = 3
}

@Singleton
class Container @Inject constructor() {

    var selection: Selection? = null
    var selectedQuoteRep: QuoteRep? = null
    var saveImage: ((Uri) -> Unit)? = null
    var saveFavorite: ((Boolean) -> Unit)? = null
    var selectedQuote: Quote? = null
    var selectedQuoteImageUri: Uri? = null
    var isDarkMode: Boolean = false

    fun reset() {
        selection = null
        selectedQuoteRep = null
        saveImage = null
        saveFavorite = null
        selectedQuote = null
        selectedQuoteImageUri = null
    }
}