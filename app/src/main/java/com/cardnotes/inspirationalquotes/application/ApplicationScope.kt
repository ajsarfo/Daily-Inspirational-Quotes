package com.cardnotes.inspirationalquotes.application

import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.repository.Repository

class ApplicationScope private constructor(
    private val repository: Repository
) {

    private val quotes = mutableSetOf<Quote>()

    fun saveQuote(quote: Quote) {
        quotes.add(quote)
    }

    suspend fun execute() {
        with(repository.database()) {
            quotes.forEach { quote ->
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
            quotes.clear()
        }
    }

    companion object {
        private var applicationScope: ApplicationScope? = null

        fun getInstance(repository: Repository): ApplicationScope {
            return applicationScope ?: ApplicationScope(repository).also {
                applicationScope = it
            }
        }
    }
}