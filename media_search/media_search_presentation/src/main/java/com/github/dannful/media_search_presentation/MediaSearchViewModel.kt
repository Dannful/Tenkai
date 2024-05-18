package com.github.dannful.media_search_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.github.dannful.core.data.model.QueryInput
import com.github.dannful.core.domain.model.MediaDate
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core_ui.components.MediaInfo
import com.github.dannful.media_search_domain.use_case.MediaSearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MediaSearchViewModel @Inject constructor(
    private val mediaSearchUseCases: MediaSearchUseCases,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var state by mutableStateOf(MediaSearchState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                genres = mediaSearchUseCases.getGenres().getOrNull() ?: return@launch
            )
            state = state.copy(
                pagingData = mediaSearchUseCases.searchMedia(state.mediaSearch)
            )
            state = state.copy(years = withContext(dispatcherProvider.Default) {
                (1940..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()
            })
            state = state.copy(
                scoreFormat = mediaSearchUseCases.getScoreFormat().getOrNull() ?: return@launch
            )
        }

    }

    fun onEvent(mediaSearchEvent: MediaSearchEvent) {
        when (mediaSearchEvent) {
            is MediaSearchEvent.SearchQuery -> state =
                state.copy(mediaSearch = state.mediaSearch.copy(query = mediaSearchEvent.query))

            is MediaSearchEvent.ToggleGenre -> {
                if (state.mediaSearch.genres.contains(mediaSearchEvent.genre)) {
                    state =
                        state.copy(mediaSearch = state.mediaSearch.copy(genres = state.mediaSearch.genres - mediaSearchEvent.genre))
                    return
                }
                state =
                    state.copy(mediaSearch = state.mediaSearch.copy(genres = state.mediaSearch.genres + mediaSearchEvent.genre))
            }

            is MediaSearchEvent.SetMediaType -> state = state.copy(
                mediaSearch = state.mediaSearch.copy(
                    type = mediaSearchEvent.mediaType
                )
            )

            is MediaSearchEvent.ToggleStartYear -> {
                if (state.mediaSearch.seasonYear == mediaSearchEvent.year) {
                    state = state.copy(mediaSearch = state.mediaSearch.copy(seasonYear = null))
                    return
                }
                state = state.copy(
                    mediaSearch = state.mediaSearch.copy(
                        seasonYear = mediaSearchEvent.year
                    )
                )
            }

            is MediaSearchEvent.SetCurrentMedia -> {
                if (mediaSearchEvent.media == null) {
                    state = state.copy(clickedMedia = null)
                    return
                }
                state =
                    state.copy(
                        clickedMedia = MediaInfo(
                            media = mediaSearchEvent.media,
                            mediaUpdate =
                            UserMediaUpdate(
                                mediaId = QueryInput.present(mediaSearchEvent.media.id),
                                status = QueryInput.present(UserMediaStatus.CURRENT),
                                startedAt = QueryInput.present(MediaDate.today),
                                progress = QueryInput.present(0),
                                score = QueryInput.present(0.toDouble())
                            ),
                            userScoreFormat = state.scoreFormat
                        )
                    )
                viewModelScope.launch {
                    val userMedia =
                        mediaSearchUseCases.getUserMedia(mediaId = mediaSearchEvent.media.id)
                            .getOrNull() ?: return@launch
                    state = state.copy(
                        clickedMedia = state.clickedMedia?.copy(
                            mediaUpdate =
                            UserMediaUpdate(
                                mediaId = QueryInput.present(mediaSearchEvent.media.id),
                                status = QueryInput.present(userMedia.status),
                                startedAt = QueryInput.presentIfNotNull(userMedia.startedAt),
                                completedAt = QueryInput.presentIfNotNull(userMedia.completedAt),
                                progress = QueryInput.present(userMedia.progress),
                                score = QueryInput.presentIfNotNull(userMedia.score)
                            )
                        )
                    )
                }
            }

            is MediaSearchEvent.SendMediaUpdate -> state.clickedMedia?.mediaUpdate?.let {
                viewModelScope.launch {
                    mediaSearchUseCases.updateUserMedia(it)
                }
            }

            is MediaSearchEvent.SetCurrentUpdate -> state = state.copy(
                clickedMedia = state.clickedMedia?.copy(mediaUpdate = mediaSearchEvent.mediaUpdate)
            )

            MediaSearchEvent.Send -> state = state.copy(
                pagingData = mediaSearchUseCases.searchMedia(state.mediaSearch)
                    .cachedIn(viewModelScope)
            )

            MediaSearchEvent.HideDateDialog -> state = state.copy(startDateDialogVisible = false)
            MediaSearchEvent.ShowDateDialog -> state = state.copy(startDateDialogVisible = true)
        }
    }
}