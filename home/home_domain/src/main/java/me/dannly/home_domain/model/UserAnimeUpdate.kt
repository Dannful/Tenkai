package me.dannly.home_domain.model

data class UserAnimeUpdate(
    val mediaId: Int,
    val mediaListStatus: String,
    val progress: Int,
    val score: Double?
)
