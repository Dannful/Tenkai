package me.dannly.home_domain.model

import me.dannly.core.domain.remote.model.UserAnimeStatus

data class CachedUserAnime(
    val id: Int,
    val progress: Int,
    val status: UserAnimeStatus,
    val score: Double?,
    val updatedAt: Int?,
    val cachedAnime: CachedAnime
)

