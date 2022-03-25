package me.dannly.home_presentation.settings.sources


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.dannly.core.domain.local.repository.FavouritesRepository
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    val favourites get() = favouritesRepository.getFavourites()

    var state by mutableStateOf(SourcesState())
        private set

    fun onEvent(event: SourcesEvent) {
        when (event) {
            is SourcesEvent.AddSource -> viewModelScope.launch {
                favouritesRepository.insertFavourite(event.favourite)
            }
            is SourcesEvent.RemoveSource -> viewModelScope.launch {
                favouritesRepository.deleteFavourite(event.favourite)
            }
            is SourcesEvent.SetNewSourceTitle -> state = state.copy(newSourceTitle = event.newTitle)
            is SourcesEvent.SetNewSourceUrl -> state = state.copy(newSourceUrl = event.newUrl)
            is SourcesEvent.SetEditDialogVisible -> state = state.copy(
                editDialogVisible = event.visible,
                currentlySelectedFavourite = if (event.visible) event.favourite else null,
                newSourceTitle = event.favourite?.name.orEmpty(),
                newSourceUrl = event.favourite?.url.orEmpty()
            )
            is SourcesEvent.SetDeleteDialogVisible -> state =
                state.copy(deleteDialogVisible = event.visible)
        }
    }
}