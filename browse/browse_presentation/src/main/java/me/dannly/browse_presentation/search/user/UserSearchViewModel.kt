package me.dannly.browse_presentation.search.user

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
import me.dannly.browse_domain.model.SearchedUser
import me.dannly.browse_domain.repository.BrowseRepository
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val browseRepository: BrowseRepository
) : ViewModel() {

    var users by mutableStateOf<Flow<PagingData<SearchedUser>>>(emptyFlow())
        private set

    var query by mutableStateOf("")
        private set

    fun onNewQuery(query: String) {
        this.query = query
    }

    fun search() {
        users = browseRepository.getPagedUserSearch(
            query = query
        ).cachedIn(viewModelScope)
    }
}