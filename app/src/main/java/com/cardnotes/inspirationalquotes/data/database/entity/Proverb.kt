package com.cardnotes.inspirationalquotes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cardnotes.inspirationalquotes.data.application.QuoteRep

@Entity(tableName = "proverb")
data class Proverb(
    @PrimaryKey
    val name: String
) : QuoteRep {
    override val rep: String
        get() = name
}
