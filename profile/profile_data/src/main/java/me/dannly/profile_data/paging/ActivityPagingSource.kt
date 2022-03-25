package me.dannly.profile_data.paging

import me.dannly.core.data.paging.BasePagingSource
import me.dannly.profile_domain.model.activity.RetrievedActivity
import me.dannly.profile_domain.repository.ProfileRepository

class ActivityPagingSource(
    private val profileRepository: ProfileRepository,
    private val userId: Int
) : BasePagingSource<RetrievedActivity>() {

    override suspend fun loadData(page: Int) =
        profileRepository.getUserActivity(userId, page).getOrThrow()

    override val perPage: Int
        get() = ACTIVITIES_PER_PAGE

    companion object {

        const val ACTIVITIES_PER_PAGE = 10
    }
}