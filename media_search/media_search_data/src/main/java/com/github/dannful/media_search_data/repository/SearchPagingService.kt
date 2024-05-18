package com.github.dannful.media_search_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.util.Constants
import com.github.dannful.media_search_data.model.SearchPagingSource
import com.github.dannful.media_search_domain.model.MediaSearch
import com.github.dannful.media_search_domain.repository.PagingService
import com.github.dannful.media_search_domain.repository.RemoteService
import kotlinx.coroutines.flow.Flow

class SearchPagingService(
    private val remoteService: RemoteService
) : PagingService {

    override fun getSearchPagingData(mediaSearch: MediaSearch): Flow<PagingData<Media>> = Pager(
        config = PagingConfig(
            pageSize = Constants.MEDIAS_PER_PAGE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = true,
            initialLoadSize = Constants.MEDIAS_PER_PAGE * 3
        ),
        pagingSourceFactory = { SearchPagingSource(remoteService, mediaSearch) }
    ).flow
}