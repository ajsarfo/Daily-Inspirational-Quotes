package com.cardnotes.inspirationalquotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.database.dao.author.AuthorDao
import com.cardnotes.inspirationalquotes.data.database.dao.author.AuthorQuoteDao
import com.cardnotes.inspirationalquotes.data.database.dao.category.CategoryDao
import com.cardnotes.inspirationalquotes.data.database.dao.category.CategoryQuoteDao
import com.cardnotes.inspirationalquotes.data.database.dao.love.LoveDao
import com.cardnotes.inspirationalquotes.data.database.dao.love.LoveQuoteDao
import com.cardnotes.inspirationalquotes.data.database.dao.popular.PopularQuoteDao
import com.cardnotes.inspirationalquotes.data.database.dao.proverb.ProverbDao
import com.cardnotes.inspirationalquotes.data.database.dao.proverb.ProverbQuoteDao
import com.cardnotes.inspirationalquotes.data.database.entity.Author
import com.cardnotes.inspirationalquotes.data.database.entity.Category
import com.cardnotes.inspirationalquotes.data.database.entity.Love
import com.cardnotes.inspirationalquotes.data.database.entity.Proverb

@Database(
    entities = [
        Author::class,
        Quote.AuthorQuote::class,
        Category::class,
        Quote.CategoryQuote::class,
        Love::class,
        Quote.LoveQuote::class,
        Proverb::class,
        Quote.ProverbQuote::class,
        Quote.PopularQuote::class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun authorDao(): AuthorDao
    abstract fun authorQuoteDao(): AuthorQuoteDao

    abstract fun categoryDao(): CategoryDao
    abstract fun categoryQuoteDao(): CategoryQuoteDao

    abstract fun loveDao(): LoveDao
    abstract fun loveQuoteDao(): LoveQuoteDao

    abstract fun proverbDao(): ProverbDao
    abstract fun proverbQuoteDao(): ProverbQuoteDao

    abstract fun popularQuoteDao(): PopularQuoteDao
}