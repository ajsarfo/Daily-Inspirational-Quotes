package com.cardnotes.inspirationalquotes.data.database.dao.proverb

import androidx.paging.PagingSource
import androidx.room.*
import com.cardnotes.inspirationalquotes.data.application.Quote

@Dao
interface ProverbQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(proverbQuotes: List<Quote.ProverbQuote>)

    @Query("select * from proverb_quote where favorite = :isFavorite")
    suspend fun quotes(isFavorite: Boolean) : List<Quote.ProverbQuote>

    @Query("select * from proverb_quote where tag like :tag")
    suspend fun quotes(tag: String) : List<Quote.ProverbQuote>

    @Query("select * from proverb_quote where tag like :tag order by random()")
    fun pagingSource(tag: String) : PagingSource<Int, Quote.ProverbQuote>

    @Update
    suspend fun update(quote: Quote.ProverbQuote)

    @Update
    suspend fun update(quotes: List<Quote.ProverbQuote>)
}