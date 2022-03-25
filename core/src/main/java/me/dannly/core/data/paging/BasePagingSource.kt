package me.dannly.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<T : Any> : PagingSource<Int, T>() {

    abstract suspend fun loadData(page: Int): List<T>

    abstract val perPage: Int

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val nextPage = params.key ?: 1
        return try {
            val response = loadData(nextPage)
            LoadResult.Page(
                data = response,
                prevKey = nextPage.minus(1).takeUnless { it <= 0 },
                nextKey = if (response.size < perPage) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}