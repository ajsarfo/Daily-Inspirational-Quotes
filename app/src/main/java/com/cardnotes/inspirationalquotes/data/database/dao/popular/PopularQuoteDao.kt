package com.cardnotes.inspirationalquotes.data.database.dao.popular

import androidx.paging.PagingSource
import androidx.room.*
import com.cardnotes.inspirationalquotes.data.application.Quote

@Dao
interface PopularQuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(popularQuote: Quote.PopularQuote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(popularQuotes: List<Quote.PopularQuote>)

    @Query("select * from popular_quote where favorite = :isFavorite")
    suspend fun favorites(isFavorite: Boolean) : List<Quote.PopularQuote>

    @Query("select * from popular_quote")
    suspend fun quotes() : List<Quote.PopularQuote>

    @Query("select * from popular_quote order by random()")
    fun pagingSource() : PagingSource<Int, Quote.PopularQuote>

    @Update
    suspend fun update(quote: Quote.PopularQuote)

    @Update
    suspend fun update(quotes: List<Quote.PopularQuote>)
}