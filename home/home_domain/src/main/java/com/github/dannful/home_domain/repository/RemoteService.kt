package com.github.dannful.home_domain.repository

import com.github.dannful.core.domain.model.PageResult
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.home_domain.model.UserMediaProgressInfo

interface RemoteService {

    suspend fun getUserMediaLists(
        page: Int,
        perPage: Int,
        status: UserMediaStatus
    ): Result<PageResult<UserMedia>>

    suspend fun getUserMediaProgress(mediaId: Int): Result<UserMediaProgressInfo>
}