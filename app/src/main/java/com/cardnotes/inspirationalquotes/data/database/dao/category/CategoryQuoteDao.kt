package com.cardnotes.inspirationalquotes.data.database.dao.category

import androidx.paging.PagingSource
import androidx.room.*
import com.cardnotes.inspirationalquotes.data.application.Quote

@Dao
interface CategoryQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryQuote: Quote.CategoryQuote)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryQuotes: List<Quote.CategoryQuote>)

    @Query("select * from category_quote where category like :category")
    suspend fun quotes(category: String) : List<Quote.CategoryQuote>

    @Query("select * from category_quote where category like :category order by random()")
    fun pagingSource(category: String) : PagingSource<Int, Quote.CategoryQuote>

    @Query("select * from category_quote order by random() limit :limit")
    suspend fun random(limit: Int) : List<Quote.CategoryQuote>

    @Query("select * from category_quote where favorite = :isFavorite")
    suspend fun favorites(isFavorite: Boolean) : List<Quote.CategoryQuote>

    @Update
    suspend fun update(quote: Quote.CategoryQuote)

    @Update
    suspend fun update(quotes: List<Quote.CategoryQuote>)
}