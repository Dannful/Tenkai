package me.dannly.browse_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import me.dannly.browse_data.mapper.toSearchedMedia
import me.dannly.browse_data.mapper.toSearchedUser
import me.dannly.browse_data.paging.AnimeSearchPagingSource
import me.dannly.browse_data.paging.UserSearchPagingSource
import me.dannly.browse_domain.model.MediaSearch
import me.dannly.browse_domain.repository.BrowseRepository
import me.dannly.core.data.mapper.toMediaSort
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.graphql.AnimeSearchQuery
import me.dannly.graphql.GenresQuery
import me.dannly.graphql.UserListQuery
import me.dannly.graphql.type.MediaSort

class BrowseRepositoryImpl(
    private val authenticatedApolloClient: Flow<ApolloClient?>,
    private val dispatcherProvider: DispatcherProvider
) : BrowseRepository {

    override suspend fun getAllGenres() = withContext(dispatcherProvider.io) {
        try {
            Result.success(
                authenticatedApolloClient
                    .first()!!.query(GenresQuery())
                    .await().data!!.genreCollection!!.filterNotNull()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMedia(
        mediaSearch: MediaSearch,
        page: Int
    ) = try {
        Result.success(
            authenticatedApolloClient
                .first()!!.query(AnimeSearchQuery(
                    animeTitle = mediaSearch.query.toInput(),
                    genres = mediaSearch.genres.toInput(),
                    sort = mediaSearch.mediaSort?.map { it.toMediaSort() }
                        .toInput(),
                    page = page.toInput()
                ))
                .await().data!!.page!!.media!!.mapNotNull { it?.fragments?.animeFragment?.toSearchedMedia() }
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun searchUsers(query: String, page: Int) = try {
        Result.success(
            authenticatedApolloClient
                .first()!!.query(
                    UserListQuery(
                        userName = query.toInput(),
                        page = page.toInput()
                    )
                )
                .await().data!!.page!!.users!!.mapNotNull { it?.fragments?.userFragment?.toSearchedUser() }
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getPagedMediaSearch(mediaSearch: MediaSearch) = Pager(
        config = PagingConfig(pageSize = AnimeSearchPagingSource.MEDIAS_PER_PAGE)
    ) {
        AnimeSearchPagingSource(this, mediaSearch)
    }.flow

    override fun getPagedUserSearch(query: String) = Pager(
        config = PagingConfig(pageSize = UserSearchPagingSource.USERS_PER_PAGE)
    ) {
        UserSearchPagingSource(this, query)
    }.flow
}