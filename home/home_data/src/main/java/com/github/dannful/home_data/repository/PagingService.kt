package com.github.dannful.home_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.util.Constants
import com.github.dannful.home_data.model.MediaListPagingSource
import com.github.dannful.home_domain.repository.PresentationService
import com.github.dannful.home_domain.repository.RemoteService
import kotlinx.coroutines.flow.Flow

class PagingService(
    private val remoteService: RemoteService
) : PresentationService {

    override fun fetchUserMediaLists(status: UserMediaStatus): Flow<PagingData<UserMedia>> = Pager(
        config = PagingConfig(
            pageSize = Constants.MEDIA_LIST_PER_PAGE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = true,
            initialLoadSize = Constants.MEDIA_LIST_PER_PAGE * 3,
//            jumpThreshold = Constants.MEDIA_LIST_PER_PAGE
        ),
        pagingSourceFactory = {
            MediaListPagingSource(remoteService, status, Constants.MEDIA_LIST_PER_PAGE)
        }
    ).flow
}