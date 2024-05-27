package com.github.dannful.core.data.worker

import android.content.Context
import android.widget.Toast
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.apollographql.apollo3.ApolloClient
import com.github.dannful.core.R
import com.github.dannful.core.data.mapper.toDataMediaListStatus
import com.github.dannful.core.data.mapper.toDomainMediaList
import com.github.dannful.core.data.mapper.toFuzzyDateInput
import com.github.dannful.core.data.mapper.toQuery
import com.github.dannful.core.data.mapper.toUserMediaUpdate
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.repository.RoomService
import com.github.dannful.models.UserMediaListMutation
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@HiltWorker
internal class UserMediaUpdateWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val apolloClientFlow: Flow<ApolloClient>,
    private val roomService: RoomService
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val INPUT_DATA_KEY = "USER_MEDIA"
    }

    override suspend fun doWork(): Result {
        try {
            val userMediaUpdate = Json.decodeFromString<UserMediaUpdate>(
                inputData.getString(INPUT_DATA_KEY) ?: return Result.failure()
            )
            val apolloClient = apolloClientFlow.first()
            val response = apolloClient.mutation(
                UserMediaListMutation(
                    mediaId = userMediaUpdate.mediaId.toQuery(),
                    status = userMediaUpdate.status.map { it?.toDataMediaListStatus() }.toQuery(),
                    score = userMediaUpdate.score.toQuery(),
                    startedAt = userMediaUpdate.startedAt.map { it?.toFuzzyDateInput() }.toQuery(),
                    completedAt = userMediaUpdate.completedAt.map { it?.toFuzzyDateInput() }
                        .toQuery(),
                    progress = userMediaUpdate.progress.toQuery()
                )
            ).execute()
            roomService.update(
                response.data?.SaveMediaListEntry?.mediaListFragment?.toDomainMediaList()
                    ?: return Result.failure()
            )
            return Result.success(
                Data.Builder().putString(
                    INPUT_DATA_KEY,
                    Json.encodeToString(response.data!!.SaveMediaListEntry!!.mediaListFragment.toUserMediaUpdate())
                ).build()
            )
        } catch (e: Exception) {
            Toast.makeText(
                appContext,
                appContext.getString(R.string.too_many_requests_slow_down_son), Toast.LENGTH_SHORT
            ).show()
            return Result.failure()
        }
    }
}