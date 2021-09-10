package com.cardnotes.inspirationalquotes.data.database.dao.love

import androidx.paging.PagingSource
import androidx.room.*
import com.cardnotes.inspirationalquotes.data.application.Quote

@Dao
interface LoveQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loveQuotes: List<Quote.LoveQuote>)

    @Query("select * from love_quote where favorite = :isFavorite")
    suspend fun favorites(isFavorite: Boolean) : List<Quote.LoveQuote>

    @Query("select * from love_quote where name like :name")
    suspend fun quotes(name: String) : List<Quote.LoveQuote>

    @Query("select * from love_quote where name like :name order by random()")
    fun pagingSource(name: String) : PagingSource<Int, Quote.LoveQuote>

    @Update
    suspend fun update(quote: Quote.LoveQuote)

    @Update
    suspend fun update(quotes: List<Quote.LoveQuote>)
}