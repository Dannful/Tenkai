package com.github.dannful.media_search_data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.util.Constants
import com.github.dannful.media_search_domain.model.MediaSearch
import com.github.dannful.media_search_domain.repository.RemoteService

class SearchPagingSource(
    private val remoteService: RemoteService,
    private val mediaSearch: MediaSearch
) : PagingSource<Int, Media>() {

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        val currentPage = params.key ?: 1
        val response = remoteService.retrieveMedias(mediaSearch, currentPage)
        val data = response.getOrNull() ?: return LoadResult.Error(
            response.exceptionOrNull() ?: Exception()
        )
        val isLastPage = data.items.size < Constants.MEDIAS_PER_PAGE
        return LoadResult.Page(
            data = data.items,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (isLastPage) null else currentPage + 1,
            itemsBefore = if(currentPage == 1) 0 else (currentPage - 1) * Constants.MEDIAS_PER_PAGE,
            itemsAfter = if(isLastPage) 0 else data.total - currentPage * Constants.MEDIAS_PER_PAGE
        )
    }
}