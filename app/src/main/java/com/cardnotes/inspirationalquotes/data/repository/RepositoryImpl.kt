package com.cardnotes.inspirationalquotes.data.repository

import android.content.Context
import androidx.room.Room
import com.cardnotes.inspirationalquotes.APP_CREATED
import com.cardnotes.inspirationalquotes.data.*
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.database.Database
import com.cardnotes.inspirationalquotes.data.database.entity.Author
import com.cardnotes.inspirationalquotes.data.database.entity.Category
import com.cardnotes.inspirationalquotes.data.database.entity.Love
import com.cardnotes.inspirationalquotes.data.database.entity.Proverb
import com.cardnotes.inspirationalquotes.data.json.*
import com.cardnotes.inspirationalquotes.editSettings
import com.cardnotes.inspirationalquotes.readSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
    ) : Repository {

    private val database = Room.databaseBuilder(
        context,
        Database::class.java,
        "app_database"
    ).build()


    override fun database(): Database = database

    //Start alarm from Alarm Maker during database setup
    override suspend fun setupDatabase(repositoryListener: Repository.RepositoryListener) {
        repositoryListener.apply {
            deserializeAuthors()
            check()
            deserializeCategories()
            check()
            deserializeLove()
            check()
            deserializeProverbs()
            check()
            deserializePopular()
            delay(200)
            //Start Alarm Maker here
            context.editSettings(APP_CREATED, true)
            Runtime.getRuntime().gc()
            check()
        }
    }

    override suspend fun isDatabaseCreated(): Boolean {
        return context.readSettings(APP_CREATED, false).first()
    }

    private suspend fun deserializeAuthors() {
        context.assets.open(AUTHOR_QUOTE_FILE_LOCATION).use {
            val quoteList: List<JsonAuthorQuote> = Json.decodeFromString(
                it.buffered().readBytes().decodeToString()
            )
            //Insert authors
            val authorQuotes = quoteList.map { jsonQuote ->
                Quote.AuthorQuote(
                    name = jsonQuote.author,
                    message = jsonQuote.text
                )
            }

            val authors = authorQuotes
                .groupBy { quote -> quote.name }
                .map { pair -> Author(name = pair.key, size = pair.value.size) }

            database.authorDao().insert(
                authors.sortedBy { author -> author.name }
            )
            database.authorQuoteDao().insert(
                authorQuotes.shuffled()
            )
        }
    }

    private suspend fun deserializeCategories() {
        context.assets.open(CATEGORY_QUOTE_FILE_LOCATION).use {
            val quoteList: List<JsonCategoryQuote> = Json.decodeFromString(
                it.buffered().readBytes().decodeToString()
            )
            database.categoryDao().insertCategoryList(
                quoteList.map { jsonCategory ->
                    Category(
                        category = jsonCategory.title.capitalize(Locale.ENGLISH),
                        size = jsonCategory.quotes.size
                    )
                }.sortedBy { category ->
                    category.category
                }
            )
            database.categoryQuoteDao().insert(
                quoteList.flatMap { jsonCategory ->
                    jsonCategory.quotes.map { category ->
                        Quote.CategoryQuote(
                            category = jsonCategory.title.capitalize(Locale.ENGLISH),
                            name = category.name,
                            message = category.message
                        )
                    }
                }.shuffled()
            )
        }
    }

    private suspend fun deserializeLove() {
        context.assets.open(LOVE_QUOTE_FILE_LOCATION).use {
            val quoteList: List<JsonLoveQuote> = Json.decodeFromString(
                it.buffered().readBytes().decodeToString()
            )
            val special =
                listOf("Thanksgiving", "New Year", "Wedding Wishes") //check for speciality
            database.loveDao().insert(
                quoteList.map { jsonQuote ->
                    val isSpecial = special.contains(jsonQuote.title)
                    Love(jsonQuote.title, special = isSpecial)
                }.sortedBy { love -> love.name }
            )
            database.loveQuoteDao().insert(
                quoteList.flatMap { jsonQuote ->
                    jsonQuote.quotes.map { message ->
                        Quote.LoveQuote(name = jsonQuote.title, message = message)
                    }
                }.shuffled()
            )
        }
    }

    private suspend fun deserializeProverbs() {
        context.assets.open(PROVERB_QUOTE_FILE_LOCATION).use {
            val quoteList: List<JsonProverbQuote> = Json.decodeFromString(
                it.buffered().readBytes().decodeToString()
            )
            database.proverbDao().insert(
                quoteList.map { jsonProverb -> Proverb(jsonProverb.title) }
                    .sortedBy { proverb -> proverb.name }
            )
            database.proverbQuoteDao().insert(
                quoteList.flatMap { jsonProverb ->
                    jsonProverb.quotes.map { proverb ->
                        Quote.ProverbQuote(
                            tag = jsonProverb.title,
                            name = proverb.name,
                            message = proverb.message
                        )
                    }
                }.shuffled()
            )
        }
    }

    private suspend fun deserializePopular() {
        context.assets.open(POPULAR_QUOTE_FILE_LOCATION).use {
            val quoteList: List<JsonPopularQuote> = Json.decodeFromString(
                it.buffered().readBytes().decodeToString()
            )
            database.popularQuoteDao().insert(
                quoteList.map { jsonPopularQuote ->
                    Quote.PopularQuote(
                        name = jsonPopularQuote.author,
                        message = jsonPopularQuote.text
                    )
                }.shuffled()
            )
        }

    }
}