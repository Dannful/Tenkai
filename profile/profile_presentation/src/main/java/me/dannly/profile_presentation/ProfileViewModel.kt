package me.dannly.profile_presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.dannly.core.util.UiEvent
import me.dannly.core.util.UiText
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_domain.model.RetrievedShow
import me.dannly.profile_domain.model.RetrievedUser
import me.dannly.profile_domain.repository.ProfileRepository
import javax.inject.Inject
import me.dannly.core.R

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val userId: Flow<Int?>,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val userId =
                savedStateHandle.get<Int>(Destination.Main.Profile.Info.route.arguments[0].name)
                    ?.takeUnless { it == -1 } ?: userId.filterNotNull().first()
            val user = profileRepository.searchUser(userId).getOrNull() ?: run {
                _uiEvent.send(UiEvent.NavigateBack)
                _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.load_failed)))
                return@launch
            }
            state = state.copy(
                user = user,
                favourites = profileRepository.getPaginatedUserFavourites(userId)
                    .cachedIn(viewModelScope),
                activities = profileRepository.getPaginatedUserActivity(
                    userId
                )
            )
        }
    }

    fun deleteActivity(id: Int, onDeleteFinished: () -> Unit) {
        viewModelScope.launch {
            profileRepository.deleteActivity(id)
            onDeleteFinished()
        }
    }
}