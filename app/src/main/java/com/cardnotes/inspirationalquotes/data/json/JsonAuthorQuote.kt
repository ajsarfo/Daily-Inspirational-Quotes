package com.cardnotes.inspirationalquotes.data.json

import kotlinx.serialization.Serializable

@Serializable
data class JsonAuthorQuote(
    val text: String,
    val author: String
)