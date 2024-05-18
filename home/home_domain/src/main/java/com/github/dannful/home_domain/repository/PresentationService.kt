package com.github.dannful.home_domain.repository

import androidx.paging.PagingData
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import kotlinx.coroutines.flow.Flow

interface PresentationService {

    fun fetchUserMediaLists(status: UserMediaStatus): Flow<PagingData<UserMedia>>
}