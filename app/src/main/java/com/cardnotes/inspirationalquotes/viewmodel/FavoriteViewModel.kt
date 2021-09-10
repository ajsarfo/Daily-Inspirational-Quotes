package com.cardnotes.inspirationalquotes.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.application.enums.Selection
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: Repository,
    private val container: Container
) : ViewModel(), BaseListInterface {

    private val _quoteList = MutableLiveData<List<Quote>>()
    val quoteList: LiveData<List<Quote>>
    get() = _quoteList

    init {
        viewModelScope.launch {
            repository.database().run {
                viewModelScope.launch {
                    val quoteList = mutableListOf<Quote>()
                    quoteList.addAll(authorQuoteDao().favorites(true))
                    quoteList.addAll(categoryQuoteDao().favorites(true))
                    quoteList.addAll(loveQuoteDao().favorites(true))
                    quoteList.addAll(popularQuoteDao().favorites(true))
                    _quoteList.value = quoteList
                }
            }
        }
    }

    override fun toolbarTitle(): String? {
        return container.selection?.let {
            when (it) {
                Selection.FAVORITE,
                -> "${it.selection} Quotes"
                else -> null
            }
        }
    }

    override fun container(): Container = container

    override fun saveQuote(quote: Quote) {
        repository.database().apply {
            viewModelScope.launch {
                when (quote) {
                    is Quote.AuthorQuote -> authorQuoteDao().update(quote)
                    is Quote.CategoryQuote -> categoryQuoteDao().update(quote)
                    is Quote.LoveQuote -> loveQuoteDao().update(quote)
                    is Quote.ProverbQuote -> proverbQuoteDao().update(quote)
                    is Quote.PopularQuote -> popularQuoteDao().update(quote)
                    else -> {
                    }
                }
            }
        }
    }

    override fun setBundle(bundle: Bundle?) {

    }
}