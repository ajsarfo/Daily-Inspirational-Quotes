package com.cardnotes.inspirationalquotes.data.json

import kotlinx.serialization.Serializable

@Serializable
data class JsonPopularQuote(
    val text: String,
    val author: String
)