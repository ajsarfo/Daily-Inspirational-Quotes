package com.cardnotes.inspirationalquotes.data.database.dao.author

import androidx.paging.PagingSource
import androidx.room.*
import com.cardnotes.inspirationalquotes.data.application.Quote


@Dao
interface AuthorQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authorQuote: Quote.AuthorQuote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authorQuotes: List<Quote.AuthorQuote>)

    @Query("select * from author_quote where name like :name")
    suspend fun quotes(name: String) : List<Quote.AuthorQuote>

    @Query("select * from author_quote where name like :name order by random()")
    fun pagingSource(name: String) : PagingSource<Int, Quote.AuthorQuote>

    @Query("select * from author_quote order by random() limit :limit")
    suspend fun random(limit: Int) : List<Quote.AuthorQuote>

    @Query("select * from author_quote where favorite = :isFavorite")
    suspend fun favorites(isFavorite: Boolean) : List<Quote.AuthorQuote>

    @Update
    suspend fun update(quote: Quote.AuthorQuote)

    @Update
    suspend fun update(quotes: List<Quote.AuthorQuote>)
}