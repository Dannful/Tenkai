package com.github.dannful.home_data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.github.dannful.core.data.mapper.toDataMediaListStatus
import com.github.dannful.core.data.mapper.toDomainMediaList
import com.github.dannful.core.domain.model.PageResult
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.util.Constants
import com.github.dannful.home_domain.model.UserMediaProgressInfo
import com.github.dannful.home_domain.repository.RemoteService
import com.github.dannful.models.UserMediaListsQuery
import com.github.dannful.models.UserMediaProgressQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext

class GraphQlRemoteService(
    private val apolloClientFlow: Flow<ApolloClient>,
    private val dataStore: DataStore<Preferences>,
    private val dispatcherProvider: DispatcherProvider
) : RemoteService {

    override suspend fun getUserMediaLists(
        page: Int,
        perPage: Int,
        status: UserMediaStatus
    ): Result<PageResult<UserMedia>> = try {
        val userId =
            dataStore.data.mapNotNull { it[intPreferencesKey(Constants.DATA_STORE_USER_ID_KEY_NAME)] }
                .first()
        val apolloClient = apolloClientFlow.first()
        val response = apolloClient.query(
            UserMediaListsQuery(
                status = Optional.present(status.toDataMediaListStatus()),
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                userId = Optional.present(userId)
            )
        ).execute()
        Result.success(
            PageResult(
                total = response.data!!.MediaListCollection!!.user!!.statistics!!.anime!!.statuses!!.find { it?.status == status.toDataMediaListStatus() }?.count
                    ?: 0,
                items = response.data!!.MediaListCollection!!.lists!!.mapNotNull { list -> list!!.entries!!.mapNotNull { it!!.mediaListFragment.toDomainMediaList() } }
                    .flatten(),
            )
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getUserMediaProgress(mediaId: Int): Result<UserMediaProgressInfo> =
        withContext(dispatcherProvider.IO) {
            try {
                val userId =
                    dataStore.data.mapNotNull { it[intPreferencesKey(Constants.DATA_STORE_USER_ID_KEY_NAME)] }
                        .first()
                val apolloClient = apolloClientFlow.first()
                val response = apolloClient.query(
                    UserMediaProgressQuery(
                        userId = Optional.present(userId),
                        mediaId = Optional.present(mediaId)
                    )
                ).execute()
                Result.success(
                    UserMediaProgressInfo(
                        progress = response.data!!.MediaList!!.progress!!,
                        episodes = response.data?.MediaList?.media?.nextAiringEpisode?.episode
                            ?: response.data?.MediaList?.media?.episodes
                    )
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}