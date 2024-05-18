package com.github.dannful.core.domain.model

data class Media(
    val id: Int,
    val description: String?,
    val genres: List<String>,
    val bannerUrl: String?,
    val coverImageUrl: String?,
    val episodes: Int?,
    val nextEpisode: Int?,
    val timeUntilNextEpisode: Int?,
    val status: MediaStatus,
    val titles: MediaTitle,
    val synonyms: List<String>
)
