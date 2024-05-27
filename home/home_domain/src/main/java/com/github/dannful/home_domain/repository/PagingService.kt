package com.github.dannful.home_domain.repository

import androidx.paging.PagingData
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.UserMediaUpdate
import kotlinx.coroutines.flow.Flow

interface PagingService {

    fun pagingService(userMediaStatus: UserMediaStatus): Flow<PagingData<UserMedia>>
}