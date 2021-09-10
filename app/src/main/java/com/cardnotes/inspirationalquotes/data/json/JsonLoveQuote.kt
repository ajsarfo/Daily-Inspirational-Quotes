package com.cardnotes.inspirationalquotes.data.json
import kotlinx.serialization.Serializable

@Serializable
class JsonLoveQuote(
    val title: String,
    val quotes: List<String>
)