package com.github.dannful.home_domain.use_case

import com.github.dannful.core.data.model.QueryInput
import com.github.dannful.core.domain.model.MediaDate
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.repository.UserMediaService
import com.github.dannful.home_domain.repository.RemoteService

class ProgressUpdate(
    private val userMediaService: UserMediaService,
    private val remoteService: RemoteService
) {

    private suspend fun update(mediaId: Int, addition: Int): Result<UserMediaUpdate?> {
        try {
            val progressInfo = remoteService.getUserMediaProgress(mediaId).getOrThrow()
            val newProgress = progressInfo.progress + addition
            if (newProgress < 0)
                return Result.success(null)
            if (newProgress >= (progressInfo.episodes ?: newProgress))
                return Result.success(
                    UserMediaUpdate(
                        mediaId = QueryInput.present(mediaId),
                        progress = QueryInput.present(newProgress),
                        status = QueryInput.present(UserMediaStatus.COMPLETED),
                        completedAt = QueryInput.present(MediaDate.today),
                        score = QueryInput.present(0.0)
                    )
                )
            userMediaService.updateUserMedia(
                UserMediaUpdate(
                    mediaId = QueryInput.present(mediaId),
                    progress = QueryInput.present(newProgress)
                )
            )
            return Result.success(null)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun increment(mediaId: Int) = update(mediaId, 1)
    suspend fun decrement(mediaId: Int) = update(mediaId, -1)
}