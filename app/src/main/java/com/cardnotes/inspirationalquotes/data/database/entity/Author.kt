package com.cardnotes.inspirationalquotes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cardnotes.inspirationalquotes.data.application.QuoteRep

@Entity(tableName = "author")
data class Author(
    @PrimaryKey
    val name: String,
    var size: Int = Integer.MAX_VALUE,
) : QuoteRep {
    override val rep: String
        get() = name
}