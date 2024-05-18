package com.github.dannful.home_data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.home_domain.repository.RemoteService

class MediaListPagingSource(
    private val remoteService: RemoteService,
    private val userMediaStatus: UserMediaStatus,
    private val perPage: Int
) : PagingSource<Int, UserMedia>() {

    override fun getRefreshKey(state: PagingState<Int, UserMedia>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserMedia> {
        val currentPage = params.key ?: 1
        val response = remoteService.getUserMediaLists(
            page = currentPage,
            perPage = perPage,
            status = userMediaStatus
        )
        val data = response.getOrNull() ?: return LoadResult.Error(response.exceptionOrNull()!!)
        val isLastPage = data.items.size < perPage
        return LoadResult.Page(
            data = data.items,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (isLastPage) null else currentPage + 1,
            itemsBefore = (currentPage - 1) * perPage,
            itemsAfter = if (isLastPage) 0 else data.total - (currentPage * perPage)
        )
    }
}