package me.dannly.browse_presentation.catalog.util

import me.dannly.core.domain.remote.model.AnimeSort

data class SortAndGenres(
    val sort: AnimeSort = AnimeSort.TITLE_ROMAJI,
    val genres: List<String> = emptyList()
)
