package me.dannly.browse_presentation.catalog

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import me.dannly.browse_domain.model.SearchedMedia

data class BrowseAnimeState(
    val anime: Flow<PagingData<SearchedMedia>> = emptyFlow(),
    val allGenres: List<String> = emptyList(),
    val searching: Boolean = false
)
