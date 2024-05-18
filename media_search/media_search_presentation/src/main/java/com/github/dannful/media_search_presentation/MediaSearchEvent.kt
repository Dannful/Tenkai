package com.github.dannful.media_search_presentation

import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.media_search_domain.model.MediaType

sealed class MediaSearchEvent {

    data class SearchQuery(val query: String) : MediaSearchEvent()
    data class ToggleGenre(val genre: String) : MediaSearchEvent()
    data class SetMediaType(val mediaType: MediaType) : MediaSearchEvent()
    data class ToggleStartYear(val year: Int) : MediaSearchEvent()
    data class SetCurrentMedia(val media: Media?) : MediaSearchEvent()
    data class SetCurrentUpdate(val mediaUpdate: UserMediaUpdate) : MediaSearchEvent()

    data object SendMediaUpdate : MediaSearchEvent()
    data object ShowDateDialog : MediaSearchEvent()
    data object HideDateDialog : MediaSearchEvent()

    data object Send : MediaSearchEvent()
}
