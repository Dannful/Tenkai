package com.github.dannful.core.data.repository

import com.apollographql.apollo3.ApolloClient
import com.github.dannful.core.data.mapper.toDataMediaListStatus
import com.github.dannful.core.data.mapper.toFuzzyDateInput
import com.github.dannful.core.data.mapper.toQuery
import com.github.dannful.core.data.mapper.toUserScoreFormat
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import com.github.dannful.core.domain.repository.UserMediaService
import com.github.dannful.models.GetViewerScoreFormatQuery
import com.github.dannful.models.UserMediaListMutation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

internal class AniListService(
    private val dispatcherProvider: DispatcherProvider,
    private val apolloClientFlow: Flow<ApolloClient>
) : UserMediaService {

    override suspend fun updateUserMedia(userMediaUpdate: UserMediaUpdate): Result<Unit> =
        withContext(dispatcherProvider.IO) {
            try {
                val apolloClient = apolloClientFlow.first()
                apolloClient.mutation(
                    UserMediaListMutation(
                        mediaId = userMediaUpdate.mediaId.toQuery(),
                        status = userMediaUpdate.status.map { it?.toDataMediaListStatus() }.toQuery(),
                        score = userMediaUpdate.score.toQuery(),
                        startedAt = userMediaUpdate.startedAt.map { it?.toFuzzyDateInput() }.toQuery(),
                        completedAt = userMediaUpdate.completedAt.map { it?.toFuzzyDateInput() }.toQuery(),
                        progress = userMediaUpdate.progress.toQuery()
                    )
                ).execute()
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getUserScoreFormat(): Result<UserScoreFormat> =
        withContext(dispatcherProvider.IO) {
            try {
                val apolloClient = apolloClientFlow.first()
                val response = apolloClient.query(
                    GetViewerScoreFormatQuery()
                ).execute()
                Result.success(response.data!!.Viewer!!.mediaListOptions!!.scoreFormat!!.toUserScoreFormat())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}