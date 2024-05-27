package com.github.dannful.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MediaTitle(
    val romaji: String,
    val english: String?,
    val native: String
)
