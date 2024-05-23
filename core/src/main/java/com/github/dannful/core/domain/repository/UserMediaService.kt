package com.github.dannful.core.domain.repository

import androidx.work.Operation
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import kotlinx.coroutines.flow.Flow

interface UserMediaService {

    fun updateUserMedia(userMediaUpdate: UserMediaUpdate): Flow<Operation.State>
    suspend fun getUserScoreFormat(): Result<UserScoreFormat>
}