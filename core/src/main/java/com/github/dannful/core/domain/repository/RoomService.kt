package com.github.dannful.core.domain.repository

import com.github.dannful.core.domain.model.UserMediaUpdate

interface RoomService {

    suspend fun update(userMediaUpdate: UserMediaUpdate): Result<Unit>
}