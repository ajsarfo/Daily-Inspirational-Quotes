package com.cardnotes.inspirationalquotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardnotes.inspirationalquotes.application.ApplicationScope
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: Repository,
    val container: Container
) : ViewModel() {

    private val _quote =  MutableLiveData<Quote>()

    val quote : LiveData<Quote>
    get() = _quote

    fun save(quote: Quote) {
       ApplicationScope.getInstance(repository).saveQuote(quote)
    }

    fun fetchQuote() {
        viewModelScope.launch {
            container.selectedQuote?.let {
                _quote.value = it
            }
        }
    }
}