package com.github.dannful.media_search_domain.model

data class MediaSearch(
    val query: String?,
    val seasonYear: Int?,
    val genres: List<String>,
    val sort: List<MediaSort>,
    val type: MediaType?
)
