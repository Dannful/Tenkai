package com.github.dannful.core.domain.repository

import com.github.dannful.core.domain.model.UserMedia

interface RoomService {

    suspend fun update(update: UserMedia): Result<Unit>
}