package com.cardnotes.inspirationalquotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.cardnotes.inspirationalquotes.application.ApplicationScope
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.repository.Repository
import kotlinx.coroutines.flow.Flow

abstract class PagingBaseViewModel(
    protected val repository: Repository,
    protected val container: Container
) : ViewModel(), BaseListInterface {

    val quoteFlow: Flow<PagingData<Quote>>? by lazy {
        getPagingFlow()
    }

    abstract override fun toolbarTitle() : String?

    protected abstract fun getPagingFlow() :Flow<PagingData<Quote>>?

    protected inline fun getPagingFlow(
        crossinline pagingSource: () -> PagingSource<Int, Quote>
    ): Flow<PagingData<Quote>> {
        return Pager(
            PagingConfig(10)
        ) { pagingSource() }
            .flow
            .cachedIn(viewModelScope)
    }

    override fun container(): Container = container

    override fun saveQuote(quote: Quote) {
       ApplicationScope.getInstance(repository).saveQuote(quote)
    }
}