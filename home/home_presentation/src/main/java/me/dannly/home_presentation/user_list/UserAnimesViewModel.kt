package me.dannly.home_presentation.user_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.core.domain.preferences.Preferences
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.home_domain.repository.RemoteRepository
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class UserAnimesViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val preferences: Preferences,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var state by mutableStateOf(UserAnimesState())
        private set

    val isGridFlow
        get() = preferences.getIsGrid()

    init {
        viewModelScope.launch {
            state = state.copy(animeList = withContext(dispatcherProvider.default) {
                UserAnimeStatus.values().associateWith {
                    remoteRepository.getPagedAnimeList(it).cachedIn(viewModelScope)
                }
            }, cachedScoreFormat = remoteRepository.getUserScoreFormat().getOrNull())
        }
    }

    fun onEvent(userAnimesEvent: UserAnimesEvent) {
        when (userAnimesEvent) {
            is UserAnimesEvent.Update -> {
                viewModelScope.launch {
                    remoteRepository.updateAnime(
                        userAnimesEvent.userAnimeUpdate
                    )
                    userAnimesEvent.onUpdate()
                }
            }
            is UserAnimesEvent.Delete -> viewModelScope.launch {
                remoteRepository.deleteAnime(userAnimesEvent.id)
                userAnimesEvent.onDelete()
            }
            is UserAnimesEvent.SetDialogScore -> state =
                state.copy(score = userAnimesEvent.score.filter {
                    it.isDigit() || it == '.'
                })
            is UserAnimesEvent.SetDialogVisible -> state =
                state.copy(dialogVisible = userAnimesEvent.visible)
        }
    }
}