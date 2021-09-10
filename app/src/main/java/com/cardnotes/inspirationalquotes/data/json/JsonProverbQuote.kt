package com.cardnotes.inspirationalquotes.data.json
import kotlinx.serialization.Serializable

@Serializable
class JsonProverbQuote(
    val title: String,
    val quotes: List<JsonProverb>
)

@Serializable
class JsonProverb(
    val message: String,
    val name: String
)