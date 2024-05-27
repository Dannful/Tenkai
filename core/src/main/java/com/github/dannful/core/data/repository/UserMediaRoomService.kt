package com.github.dannful.core.data.repository

import com.github.dannful.core.data.entity.UserMediaEntity
import com.github.dannful.core.data.model.UserMediaDatabase
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.domain.repository.RoomService
import kotlinx.coroutines.withContext

internal class UserMediaRoomService(
    private val dispatcherProvider: DispatcherProvider,
    private val userMediaDatabase: UserMediaDatabase
) : RoomService {

    override suspend fun update(userMediaUpdate: UserMediaUpdate): Result<Unit> =
        withContext(dispatcherProvider.IO) {
            try {
                val currentValue =
                    userMediaDatabase.userMediaDao().getByMediaId(
                        userMediaUpdate.mediaId.value
                            ?: return@withContext Result.failure(Exception("No media in cache"))
                    )
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
}