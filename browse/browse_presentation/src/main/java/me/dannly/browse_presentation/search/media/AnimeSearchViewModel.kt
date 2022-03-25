package me.dannly.browse_presentation.search.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import me.dannly.browse_domain.model.MediaSearch
import me.dannly.browse_domain.model.SearchedMedia
import me.dannly.browse_domain.repository.BrowseRepository
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(
    private val browseRepository: BrowseRepository
) : ViewModel() {

    var animeList by mutableStateOf<Flow<PagingData<SearchedMedia>>>(emptyFlow())
        private set

    var query by mutableStateOf("")
        private set

    fun setNewQuery(query: String) {
        this.query = query
    }

    fun search() {
        animeList = browseRepository.getPagedMediaSearch(
            MediaSearch(query = query)
        ).cachedIn(viewModelScope)
    }
}