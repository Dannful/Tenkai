package com.github.dannful.media_search_domain.use_case

import com.github.dannful.media_search_domain.model.MediaSearch
import com.github.dannful.media_search_domain.repository.PagingService

class SearchMedia(
    private val pagingService: PagingService
) {

    operator fun invoke(mediaSearch: MediaSearch) = pagingService.getSearchPagingData(mediaSearch)
}