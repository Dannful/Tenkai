package me.dannly.profile_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.graphql.ActivityMutation
import me.dannly.graphql.ActivityQuery
import me.dannly.graphql.UserQuery
import me.dannly.profile_data.mapper.toRetrievedActivity
import me.dannly.profile_data.mapper.toRetrievedShow
import me.dannly.profile_data.mapper.toRetrievedUser
import me.dannly.profile_data.paging.ActivityPagingSource
import me.dannly.profile_data.paging.FavouritesPagingSource
import me.dannly.profile_domain.model.RetrievedUser
import me.dannly.profile_domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val authenticatedApolloClient: Flow<ApolloClient?>,
    private val dispatcherProvider: DispatcherProvider
) : ProfileRepository {

    override suspend fun searchUser(userId: Int): Result<RetrievedUser> =
        withContext(dispatcherProvider.io) {
            try {
                Result.success(
                    authenticatedApolloClient.first()!!.query(
                        UserQuery(userId = userId.toInput())
                    ).await().data!!.user!!.fragments.userFragment.toRetrievedUser()
                )
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getUserActivity(userId: Int, page: Int) = try {
        Result.success(
            authenticatedApolloClient.first()!!.query(
                ActivityQuery(
                    page = page.toInput(),
                    userId = userId.toInput()
                )
            ).await().data!!.page!!.activities!!.filterNotNull().map {
                it.toRetrievedActivity()
            }
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteActivity(id: Int) = withContext(dispatcherProvider.io) {
        try {
            Result.success(
                authenticatedApolloClient.first()
                    !!.mutate(ActivityMutation(id = id.toInput()))
                    .await().data!!.deleteActivity!!.deleted!!
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getPaginatedUserActivity(userId: Int) = Pager(
        config = PagingConfig(pageSize = ActivityPagingSource.ACTIVITIES_PER_PAGE)
    ) {
        ActivityPagingSource(this, userId)
    }.flow

    override suspend fun searchUserFavourites(userId: Int, page: Int) = try {
        Result.success(authenticatedApolloClient.first()
            !!.query(
                UserQuery(
                    userId = userId.toInput(),
                    page = page.toInput(),
                    perPage = FavouritesPagingSource.FAVOURITES_PER_PAGE.toInput()
                )
            ).await().data!!.user!!.fragments.userFragment.favourites!!.anime!!.nodes!!
            .mapNotNull { it?.fragments?.animeFragment?.toRetrievedShow() })
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getPaginatedUserFavourites(userId: Int) = Pager(
        config = PagingConfig(pageSize = FavouritesPagingSource.FAVOURITES_PER_PAGE)
    ) {
        FavouritesPagingSource(this, userId)
    }.flow
}