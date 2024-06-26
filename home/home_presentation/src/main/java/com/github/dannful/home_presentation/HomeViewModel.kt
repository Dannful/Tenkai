package com.github.dannful.home_presentation

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
import com.github.dannful.core_ui.components.MediaInfo
import com.github.dannful.home_domain.use_case.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases
) : ViewModel() {

    var state by mutableStateOf(
        HomeState(
            pagingData = UserMediaStatus.entries.minus(UserMediaStatus.UNKNOWN).map {
                homeUseCases.fetchMediaLists(it).cachedIn(viewModelScope)
            },
        )
    )
        private set

    private val _uiRefreshes = MutableSharedFlow<Unit>()
    val uiRefreshes = _uiRefreshes.asSharedFlow()
    private lateinit var progressUpdateJob: Job

    init {
        viewModelScope.launch {
            state = state.copy(
                userScoreFormat = homeUseCases.fetchUserScoreFormat().getOrNull() ?: return@launch
            )
        }
    }

    fun onEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.SetCurrentTab -> state = state.copy(selectedTab = homeEvent.newTab)
            is HomeEvent.UpdateMediaList -> {
                viewModelScope.launch {
                    homeUseCases.updateMediaList(
                        homeEvent.userMediaUpdate
                    )
                }
            }

            is HomeEvent.DecreaseProgress -> progressUpdateJob = viewModelScope.launch {
                if (::progressUpdateJob.isInitialized && progressUpdateJob.isActive)
                    progressUpdateJob.cancel()
                val progressWithEpisodes =
                    homeUseCases.fetchProgress(homeEvent.mediaId).getOrNull() ?: return@launch
                if (progressWithEpisodes.progress == 0)
                    return@launch
                onEvent(
                    HomeEvent.UpdateMediaList(
                        UserMediaUpdate(
                            mediaId = QueryInput.present(homeEvent.mediaId),
                            progress = QueryInput.present(progressWithEpisodes.progress - 1)
                        )
                    )
                )
            }

            is HomeEvent.IncreaseProgress -> progressUpdateJob = viewModelScope.launch {
                if (::progressUpdateJob.isInitialized && progressUpdateJob.isActive)
                    progressUpdateJob.cancel()
                val progressWithEpisodes =
                    homeUseCases.fetchProgress(homeEvent.mediaId).getOrNull() ?: return@launch
                if (progressWithEpisodes.progress >= (progressWithEpisodes.episodes
                        ?: progressWithEpisodes.progress)
                )
                    return@launch
                if (progressWithEpisodes.progress == progressWithEpisodes.episodes?.minus(1)) {
                    state = state.copy(
                        swipedMediaUpdate = UserMediaUpdate(
                            mediaId = QueryInput.present(homeEvent.mediaId),
                            score = QueryInput.present(0.0),
                            status = QueryInput.present(UserMediaStatus.COMPLETED),
                            progress = QueryInput.present(progressWithEpisodes.progress + 1),
                            completedAt = QueryInput.present(MediaDate.today)
                        )
                    )
                    return@launch
                }
                onEvent(
                    HomeEvent.UpdateMediaList(
                        UserMediaUpdate(
                            mediaId = QueryInput.present(homeEvent.mediaId),
                            progress = QueryInput.present(progressWithEpisodes.progress + 1)
                        )
                    )
                )
            }

            is HomeEvent.ShowDialog -> {
                state = state.copy(
                    dialogMedia = MediaInfo(
                        media = homeEvent.mediaWithUpdate.media,
                        mediaUpdate = homeEvent.mediaWithUpdate.update,
                        userScoreFormat = state.userScoreFormat
                    )
                )
            }

            HomeEvent.HideDialog -> state = state.copy(dialogMedia = null)

            is HomeEvent.UpdateDialogEditInfo -> state = state.copy(
                dialogMedia = state.dialogMedia?.copy(
                    mediaUpdate = homeEvent.userMediaUpdate
                )
            )

            HomeEvent.RefreshUi -> viewModelScope.launch {
                _uiRefreshes.emit(Unit)
            }

            is HomeEvent.UpdateScore -> state =
                state.copy(
                    swipedMediaUpdate = state.swipedMediaUpdate?.copy(
                        score = QueryInput.present(
                            homeEvent.score
                        )
                    )
                )

            HomeEvent.HideScoreAlert -> state = state.copy(swipedMediaUpdate = null)
        }
    }
}