package me.dannly.browse_data.paging

import me.dannly.browse_domain.model.SearchedUser
import me.dannly.browse_domain.repository.BrowseRepository
import me.dannly.core.data.paging.BasePagingSource

class UserSearchPagingSource(
    private val browseRepository: BrowseRepository,
    private val query: String
) : BasePagingSource<SearchedUser>() {

    override suspend fun loadData(page: Int): List<SearchedUser> = browseRepository
        .searchUsers(query, page).getOrThrow()

    override val perPage: Int
        get() = USERS_PER_PAGE

    companion object {

        const val USERS_PER_PAGE = 10
    }
}