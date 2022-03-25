package me.dannly.home_domain.model

import me.dannly.core.domain.remote.model.AnimeStatus

data class CachedAnime(
    val id: Int,
    val title: String,
    val synonyms: List<String>,
    val averageScore: Int?,
    val synopsis: String?,
    val genres: List<String>,
    val episodes: Int?,
    val nextEpisode: Int?,
    val bannerUrl: String?,
    val timeUntilNextEpisode: Int?,
    val status: AnimeStatus,
    val coverUrl: String?
) {

    val episodeCount get() = nextEpisode?.minus(1) ?: episodes ?: 0
}
