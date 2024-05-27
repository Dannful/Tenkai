package com.github.dannful.home_data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.dannful.core.data.entity.UserMediaEntity
import com.github.dannful.core.data.model.UserMediaDatabase
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.util.Constants
import com.github.dannful.home_data.model.UserMediaRemoteMediator
import com.github.dannful.home_domain.repository.PagingService
import com.github.dannful.home_domain.repository.RemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomPagingService(
    private val dispatcherProvider: DispatcherProvider,
    private val remoteService: RemoteService,
    private val userMediaDatabase: UserMediaDatabase
) : PagingService {

    override suspend fun update(userMediaUpdate: UserMediaUpdate): Result<Unit> =
        withContext(dispatcherProvider.IO) {
            try {
                val currentValue =
                    userMediaDatabase.userMediaDao().getByMediaId(userMediaUpdate.mediaId.value!!)
                userMediaDatabase.userMediaDao().update(
                    UserMediaEntity(
                        userMediaId = currentValue.userMediaId,
                        title = currentValue.title,
                        mediaId = currentValue.mediaId,
                        status = userMediaUpdate.status.getOrDefault(currentValue.status)
                            ?: currentValue.status,
                        startedAt = userMediaUpdate.startedAt.getOrDefault(currentValue.startedAt),
                        completedAt = userMediaUpdate.completedAt.getOrDefault(currentValue.completedAt),
                        progress = userMediaUpdate.progress.getOrDefault(currentValue.progress)
                            ?: 0,
                        userMediaStatus = userMediaUpdate.score.getOrDefault(currentValue.userMediaStatus)
                            ?: 0.0,
                        updatedAt = (System.currentTimeMillis() * 1000).toInt()
                    )
                )
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun pagingService(userMediaStatus: UserMediaStatus): Flow<PagingData<UserMedia>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.MEDIA_LIST_PER_PAGE,
                prefetchDistance = Constants.PREFETCH_DISTANCE,
                enablePlaceholders = true,
                initialLoadSize = Constants.MEDIA_LIST_PER_PAGE * 3,
            ),
            pagingSourceFactory = {
                userMediaDatabase.userMediaDao().pagingSource(userMediaStatus)
            },
            remoteMediator = UserMediaRemoteMediator(
                remoteService = remoteService,
                userMediaStatus = userMediaStatus,
                userMediaDatabase = userMediaDatabase,
                dispatcherProvider = dispatcherProvider
            )
        ).flow.map { pagingData ->
            pagingData.map pagingDataMapper@{
                it.toUserMedia(userMediaDatabase.mediaDao().get(it.mediaId))
            }
        }
    }
}