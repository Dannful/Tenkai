package me.dannly.browse_domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.dannly.browse_domain.model.MediaSearch
import me.dannly.browse_domain.model.SearchedMedia
import me.dannly.browse_domain.model.SearchedUser

interface BrowseRepository {

    suspend fun getAllGenres(): Result<List<String>>
    suspend fun searchMedia(mediaSearch: MediaSearch, page: Int): Result<List<SearchedMedia>>
    suspend fun searchUsers(query: String, page: Int): Result<List<SearchedUser>>

    fun getPagedMediaSearch(mediaSearch: MediaSearch): Flow<PagingData<SearchedMedia>>
    fun getPagedUserSearch(query: String): Flow<PagingData<SearchedUser>>
}