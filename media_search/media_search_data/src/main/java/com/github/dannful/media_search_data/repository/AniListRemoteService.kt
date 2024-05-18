package com.github.dannful.media_search_data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.github.dannful.core.data.mapper.toDomainMedia
import com.github.dannful.core.data.mapper.toDomainMediaList
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.PageResult
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.util.Constants
import com.github.dannful.media_search_data.mapper.toDataMediaSort
import com.github.dannful.media_search_data.mapper.toDataMediaType
import com.github.dannful.media_search_domain.model.MediaSearch
import com.github.dannful.media_search_domain.repository.RemoteService
import com.github.dannful.models.GenresQuery
import com.github.dannful.models.GetUserMediaQuery
import com.github.dannful.models.MediaSearchQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext

class AniListRemoteService(
    private val apolloClientFlow: Flow<ApolloClient>,
    private val dispatcherProvider: DispatcherProvider,
    private val dataStore: DataStore<Preferences>
) : RemoteService {

    override suspend fun retrieveMedias(
        mediaSearch: MediaSearch,
        page: Int
    ): Result<PageResult<Media>> =
        try {
            val apolloClient = apolloClientFlow.first()
            val response = apolloClient.query(
                MediaSearchQuery(
                    query = Optional.presentIfNotNull(mediaSearch.query?.takeIf { it.isNotEmpty() }),
                    perPage = Optional.present(Constants.MEDIAS_PER_PAGE),
                    page = Optional.present(page),
                    genres = Optional.presentIfNotNull(mediaSearch.genres.takeIf { it.isNotEmpty() }),
                    seasonYear = Optional.presentIfNotNull(mediaSearch.seasonYear),
                    sort = Optional.presentIfNotNull(mediaSearch.sort.map { it.toDataMediaSort() }
                        .takeIf { it.isNotEmpty() }),
                    type = Optional.presentIfNotNull(mediaSearch.type?.toDataMediaType()),
                )
            ).execute()
            Result.success(
                PageResult(
                    items = response.data!!.Page!!.media!!.mapNotNull { it?.mediaFragment?.toDomainMedia() },
                    total = response.data!!.Page!!.pageInfo!!.lastPage!! * Constants.MEDIAS_PER_PAGE
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }

    override suspend fun getUserMedia(mediaId: Int): Result<UserMedia> =
        withContext(dispatcherProvider.IO) {
            try {
                val userId =
                    dataStore.data.mapNotNull { it[intPreferencesKey(Constants.DATA_STORE_USER_ID_KEY_NAME)] }
                        .first()
                val apolloClient = apolloClientFlow.first()
                val response = apolloClient.query(
                    GetUserMediaQuery(
                        userId = Optional.present(userId),
                        mediaId = Optional.present(mediaId)
                    )
                ).execute()
                Result.success(response.data!!.MediaList!!.mediaListFragment.toDomainMediaList())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getGenreList(): Result<List<String>> = withContext(dispatcherProvider.IO) {
        try {
            val apolloClient = apolloClientFlow.first()
            val response = apolloClient.query(
                GenresQuery()
            ).execute()
            Result.success(response.data?.GenreCollection?.filterNotNull() ?: emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}