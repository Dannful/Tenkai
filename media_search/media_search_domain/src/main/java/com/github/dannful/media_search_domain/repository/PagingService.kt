package com.github.dannful.media_search_domain.repository

import androidx.paging.PagingData
import com.github.dannful.core.domain.model.Media
import com.github.dannful.media_search_domain.model.MediaSearch
import kotlinx.coroutines.flow.Flow

interface PagingService {

    fun getSearchPagingData(mediaSearch: MediaSearch): Flow<PagingData<Media>>
}