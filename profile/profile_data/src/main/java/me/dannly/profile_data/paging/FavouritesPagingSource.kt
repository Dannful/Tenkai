package me.dannly.profile_data.paging

import me.dannly.core.data.paging.BasePagingSource
import me.dannly.profile_domain.model.RetrievedShow
import me.dannly.profile_domain.repository.ProfileRepository

class FavouritesPagingSource(
    private val profileRepository: ProfileRepository,
    private val userId: Int
) : BasePagingSource<RetrievedShow>() {

    override suspend fun loadData(page: Int): List<RetrievedShow> =
        profileRepository.searchUserFavourites(userId, page).getOrThrow()

    override val perPage: Int
        get() = FAVOURITES_PER_PAGE

    companion object {

        const val FAVOURITES_PER_PAGE = 10
    }
}