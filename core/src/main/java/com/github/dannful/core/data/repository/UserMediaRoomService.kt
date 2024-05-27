package com.github.dannful.core.data.repository

import androidx.room.withTransaction
import com.github.dannful.core.data.entity.toEntity
import com.github.dannful.core.data.model.UserMediaDatabase
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.domain.repository.RoomService
import kotlinx.coroutines.withContext

internal class UserMediaRoomService(
    private val dispatcherProvider: DispatcherProvider,
    private val userMediaDatabase: UserMediaDatabase
) : RoomService {

    override suspend fun update(update: UserMedia): Result<Unit> =
        withContext(dispatcherProvider.IO) {
            try {
                userMediaDatabase.withTransaction {
                    userMediaDatabase.mediaDao().insert(
                        update.media.toEntity()
                    )
                    userMediaDatabase.userMediaDao().insert(
                        update.toEntity()
                    )
                }
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}