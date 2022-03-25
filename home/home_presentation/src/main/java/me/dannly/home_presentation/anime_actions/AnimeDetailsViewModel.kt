package me.dannly.home_presentation.anime_actions

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.dannly.core.R
import me.dannly.core.domain.local.repository.FavouritesRepository
import me.dannly.core.domain.preferences.Preferences
import me.dannly.core.util.UiEvent
import me.dannly.core.util.UiText
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_domain.repository.LocalRepository
import me.dannly.home_domain.repository.RemoteRepository
import javax.inject.Inject

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalCoroutinesApi::class
)
@HiltViewModel
class AnimeDetailsViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val preferences: Preferences,
    private val remoteRepository: RemoteRepository,
    private val favouritesRepository: FavouritesRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    val returnToMainScreen
        get() = preferences.getShouldReturnToMainScreen()

    var state by mutableStateOf(AnimeDetailsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun loadFailed(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            _uiEvent.send(UiEvent.NavigateBack)
            _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.load_failed)))
        }
    }

    private fun setupStateFields() {
        favouritesRepository.getFavourites().onEach {
            state = state.copy(favouriteWithSources = it)
        }.launchIn(viewModelScope)
        val animeId =
            savedStateHandle.get<Int>(Destination.AnimeActions.Info.route.arguments[0].name)
                .takeUnless { it == -1 } ?: run {
                if (state.cachedAnime == null)
                    loadFailed(viewModelScope)
                return
            }
        viewModelScope.launch {
            val cachedUserAnime =
                localRepository.getById(animeId) ?: remoteRepository.getUserAnimeByMediaId(animeId)
                    .getOrElse {
                        state = state.copy(
                            cachedAnime = remoteRepository.getAnimeById(animeId).getOrElse {
                                loadFailed(this)
                                return@launch
                            })
                        return@launch
                    }
            state = state.copy(
                cachedUserAnime = cachedUserAnime,
                cachedAnime = cachedUserAnime.cachedAnime,
                score = cachedUserAnime.score.toString(),
                sliderProgress = cachedUserAnime.progress.toFloat(),
                sliderText = cachedUserAnime.progress.toString(),
                status = cachedUserAnime.status
            )
        }
    }

    init {
        setupStateFields()
    }

    fun resetUserAnime() {
        state = state.copy(cachedUserAnime = null)
    }

    fun onEditEvent(animeEditEvent: AnimeDetailsEvent.AnimeEditEvent) {
        when (animeEditEvent) {
            is AnimeDetailsEvent.AnimeEditEvent.SetSliderProgress -> state =
                state.copy(sliderProgress = animeEditEvent.newProgress)
            is AnimeDetailsEvent.AnimeEditEvent.SetScore -> state =
                state.copy(score = animeEditEvent.newScore)
            is AnimeDetailsEvent.AnimeEditEvent.SetStatus -> state =
                state.copy(status = animeEditEvent.newStatus)
            is AnimeDetailsEvent.AnimeEditEvent.UpdateUserAnime -> viewModelScope.launch {
                state = state.copy(updating = true)
                remoteRepository.updateAnime(animeEditEvent.userAnimeUpdate).getOrNull()
                    ?.let {
                        animeEditEvent.updateFinished(it)
                    }
                state = state.copy(updating = false)
            }
            is AnimeDetailsEvent.AnimeEditEvent.DeleteUserAnime -> viewModelScope.launch {
                remoteRepository.deleteAnime(animeEditEvent.id)
            }
            is AnimeDetailsEvent.AnimeEditEvent.SetSliderText -> state =
                state.copy(sliderText = animeEditEvent.newText)
            is AnimeDetailsEvent.AnimeEditEvent.SetStatusPopupVisible -> state =
                state.copy(statusPopupVisible = animeEditEvent.visible)
        }
    }

    val currentlySelectedSource
        get() = state.favouriteWithSources.getOrNull(state.currentlySelectedSourceIndex)

    fun onWatchEvent(animeWatchEvent: AnimeDetailsEvent.AnimeWatchEvent) {
        when (animeWatchEvent) {
            is AnimeDetailsEvent.AnimeWatchEvent.SetSourceIndex -> state = state.copy(
                currentlySelectedSourceIndex = animeWatchEvent.index
            )
            is AnimeDetailsEvent.AnimeWatchEvent.AddSource -> viewModelScope.launch(Dispatchers.IO) {
                favouritesRepository.insertSource(
                    animeWatchEvent.source
                )
            }
            is AnimeDetailsEvent.AnimeWatchEvent.RemoveSource -> viewModelScope.launch(
                Dispatchers.IO
            ) {
                favouritesRepository.deleteSource(animeWatchEvent.source)
            }
            is AnimeDetailsEvent.AnimeWatchEvent.SetAdd -> state =
                state.copy(buttonAdd = animeWatchEvent.add)
            is AnimeDetailsEvent.AnimeWatchEvent.SetUrl -> state =
                state.copy(currentUrl = animeWatchEvent.url)
        }
    }
}