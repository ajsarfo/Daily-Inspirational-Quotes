package com.cardnotes.inspirationalquotes.data.json
import kotlinx.serialization.Serializable

@Serializable
data class JsonCategoryQuote(
    val title: String,
    val quotes: List<JsonCategory>
)

@Serializable
data class JsonCategory(
    val message: String,
    val name: String
)