package com.cardnotes.inspirationalquotes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cardnotes.inspirationalquotes.data.application.QuoteRep

@Entity(tableName = "love")
class Love(
    @PrimaryKey
    val name: String,
    val special: Boolean = false
) : QuoteRep {
    override val rep: String
        get() = name
}