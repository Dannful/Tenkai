package com.github.dannful.core.data.repository

import android.app.Application
import androidx.lifecycle.asFlow
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Operation
import androidx.work.WorkManager
import com.apollographql.apollo3.ApolloClient
import com.github.dannful.core.data.mapper.toUserScoreFormat
import com.github.dannful.core.data.worker.UserMediaUpdateWorker
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import com.github.dannful.core.domain.repository.UserMediaService
import com.github.dannful.models.GetViewerScoreFormatQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class AniListService(
    private val dispatcherProvider: DispatcherProvider,
    private val apolloClientFlow: Flow<ApolloClient>,
    private val application: Application
) : UserMediaService {

    override fun updateUserMedia(userMediaUpdate: UserMediaUpdate): Flow<Operation.State> {
        val request = OneTimeWorkRequestBuilder<UserMediaUpdateWorker>()
            .setInputData(
                Data.Builder().putString(
                    UserMediaUpdateWorker.INPUT_DATA_KEY,
                    Json.encodeToString(userMediaUpdate)
                ).build()
            )
            .build()
        return WorkManager.getInstance(application).enqueue(request).state.asFlow()
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