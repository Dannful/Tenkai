package com.github.dannful.core.domain.repository

import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat

interface UserMediaService {

    suspend fun updateUserMedia(userMediaUpdate: UserMediaUpdate): Result<Unit>
    suspend fun getUserScoreFormat(): Result<UserScoreFormat>
}