package me.dannly.browse_domain.model

import me.dannly.core.domain.remote.model.AnimeSort

data class MediaSearch(
    val query: String? = null,
    val genres: List<String>? = null,
    val mediaSort: List<AnimeSort>? = null
)
