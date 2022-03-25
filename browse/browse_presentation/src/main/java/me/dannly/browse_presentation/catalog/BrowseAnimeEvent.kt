package me.dannly.browse_presentation.catalog

import me.dannly.core.domain.remote.model.AnimeSort

sealed class BrowseAnimeEvent {

    data class SetScrollPosition(val scrollPosition: Int) : BrowseAnimeEvent()
    data class SetSort(val mediaSort: AnimeSort) : BrowseAnimeEvent()
    data class SelectGenre(val genre: String) : BrowseAnimeEvent()
    data class UnselectGenre(val genre: String) : BrowseAnimeEvent()
    data class SetSearching(val searching: Boolean): BrowseAnimeEvent()
}
