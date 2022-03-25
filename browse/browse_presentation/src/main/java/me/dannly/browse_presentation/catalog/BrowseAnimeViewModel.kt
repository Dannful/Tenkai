package me.dannly.browse_presentation.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.dannly.browse_domain.model.MediaSearch
import me.dannly.browse_domain.repository.BrowseRepository
import me.dannly.browse_presentation.catalog.util.SortAndGenres
import me.dannly.core.domain.remote.model.AnimeSort
import javax.inject.Inject

@HiltViewModel
class BrowseAnimeViewModel @Inject constructor(
    private val browseRepository: BrowseRepository
) : ViewModel() {

    var state by mutableStateOf(BrowseAnimeState())
        private set

    private val _sortAndGenres = MutableSharedFlow<SortAndGenres>(1)
    private val sortAndGenres get() = _sortAndGenres.asSharedFlow()

    private var _scrollPosition = 0
    val scrollPosition get() = _scrollPosition

    fun getLastSortAndGenres() = sortAndGenres.replayCache.getOrElse(0) {
        SortAndGenres()
    }

    init {
        viewModelScope.launch {
            val genres = browseRepository.getAllGenres().getOrNull() ?: return@launch
            state = state.copy(allGenres = genres)
        }
        sortAndGenres.onEach {
            state = state.copy(
                anime = browseRepository.getPagedMediaSearch(
                    MediaSearch(
                        mediaSort = listOf(it.sort),
                        genres = it.genres
                    )
                ).cachedIn(viewModelScope)
            )
        }.launchIn(viewModelScope)
        viewModelScope.launch {
            _sortAndGenres.emit(
                SortAndGenres(
                    sort = AnimeSort.POPULARITY_DESC
                )
            )
        }
    }

    fun onEvent(eventAnime: BrowseAnimeEvent) {
        when (eventAnime) {
            is BrowseAnimeEvent.SelectGenre -> viewModelScope.launch {
                val lastValue = getLastSortAndGenres()
                _sortAndGenres.emit(lastValue.copy(genres = lastValue.genres + eventAnime.genre))
            }
            is BrowseAnimeEvent.UnselectGenre -> viewModelScope.launch {
                val lastValue = getLastSortAndGenres()
                _sortAndGenres.emit(lastValue.copy(genres = lastValue.genres - eventAnime.genre))
            }
            is BrowseAnimeEvent.SetSort -> viewModelScope.launch {
                val lastValue = getLastSortAndGenres()
                _sortAndGenres.emit(lastValue.copy(sort = eventAnime.mediaSort))
            }
            is BrowseAnimeEvent.SetScrollPosition -> _scrollPosition = eventAnime.scrollPosition
            is BrowseAnimeEvent.SetSearching -> state =
                state.copy(searching = eventAnime.searching)
        }
    }
}