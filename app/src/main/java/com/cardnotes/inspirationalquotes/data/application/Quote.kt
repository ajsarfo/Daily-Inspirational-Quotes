package com.cardnotes.inspirationalquotes.data.application

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.cardnotes.inspirationalquotes.data.database.entity.Author
import com.cardnotes.inspirationalquotes.data.database.entity.Category
import com.cardnotes.inspirationalquotes.data.database.entity.Love
import com.cardnotes.inspirationalquotes.data.database.entity.Proverb
import java.util.*


sealed class Quote(
    open val id: Int = 0,
    open val name: String = "",
    open val message: String = "",
    open var favorite: Boolean = false
) {
    @Entity(
        tableName = "author_quote",
        foreignKeys = [
            ForeignKey(
                entity = Author::class,
                parentColumns = ["name"],
                childColumns = ["name"],
                onDelete = ForeignKey.CASCADE
            )
        ]
    )
    data class AuthorQuote(
        @PrimaryKey(autoGenerate = true) override val id: Int = 0,
        @ColumnInfo(index = true) override val name: String,
        override val message: String,
        override var favorite: Boolean = false
    ) : Quote()

    @Entity(
        tableName = "category_quote",
        foreignKeys = [
            ForeignKey(
                entity = Category::class,
                parentColumns = ["category"],
                childColumns = ["category"],
                onDelete = ForeignKey.CASCADE
            )
        ]
    )
    data class CategoryQuote(
        @PrimaryKey(autoGenerate = true) override val id: Int = 0,
        @ColumnInfo(index = true) val category: String,
        override val name: String,
        override val message: String,
        override var favorite: Boolean = false
    ) : Quote()

    @Entity(
        tableName = "love_quote",
        foreignKeys = [
            ForeignKey(
                entity = Love::class,
                parentColumns = ["name"],
                childColumns = ["name"],
                onDelete = ForeignKey.CASCADE
            )
        ]
    )
    data class LoveQuote(
        @PrimaryKey(autoGenerate = true) override val id: Int = 0,
        @ColumnInfo(index = true) override val name: String,
        override val message: String,
        override var favorite: Boolean = false
    ) : Quote()

    @Entity(
        tableName = "proverb_quote",
        foreignKeys = [
            ForeignKey(
                entity = Proverb::class,
                parentColumns = ["name"],
                childColumns = ["tag"],
                onDelete = ForeignKey.CASCADE
            )
        ]
    )
    data class ProverbQuote(
        @PrimaryKey(autoGenerate = true) override val id: Int = 0,
        @ColumnInfo(index = true) val tag: String,
        override val name: String,
        override val message: String,
        override var favorite: Boolean = false
    ) : Quote()

    @Entity(tableName = "popular_quote")
    data class PopularQuote(
        @PrimaryKey(autoGenerate = true) override val id: Int = 0,
        override val name: String,
        override val message: String,
        override var favorite: Boolean = false
    ) : Quote()

    class Separator(
        override val id: Int = -1,
        override val name: String = UUID.randomUUID().toString(),
    ) : Quote()
}
