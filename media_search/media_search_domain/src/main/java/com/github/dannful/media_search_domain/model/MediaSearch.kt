package com.github.dannful.media_search_domain.model

data class MediaSearch(
    val query: String?,
    val seasonYear: Int?,
    val genres: Set<String>,
    val tags: Set<String>,
    val sort: List<MediaSort>,
    val type: MediaType?
)
