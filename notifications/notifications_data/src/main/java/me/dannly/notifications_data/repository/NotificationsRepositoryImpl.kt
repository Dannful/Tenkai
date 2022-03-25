package me.dannly.notifications_data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.graphql.CurrentlyAuthenticatedUserQuery
import me.dannly.graphql.NotificationQuery
import me.dannly.notifications_data.mapper.toRetrievedNotification
import me.dannly.notifications_data.paging.NotificationsPager
import me.dannly.notifications_domain.repository.NotificationsRepository

class NotificationsRepositoryImpl(
    private val authenticatedApolloClient: Flow<ApolloClient?>,
    private val dispatcherProvider: DispatcherProvider
) : NotificationsRepository {

    override suspend fun retrieveNotifications(page: Int, perPage: Int?) = try {
        Result.success(
            authenticatedApolloClient.first()
            !!.query(
                NotificationQuery(
                    page = page.toInput(),
                    resetNotificationCount = true.toInput(),
                    perPage = perPage?.toInput()
                        ?: NotificationsPager.NOTIFICATIONS_PER_PAGE.toInput()
                )
            ).await().data!!.page!!.notifications!!.mapNotNull { it?.toRetrievedNotification() }
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun retrieveUnreadNotificationCount() = withContext(dispatcherProvider.io) {
        try {
            Result.success(
                authenticatedApolloClient.first()
                !!.query(CurrentlyAuthenticatedUserQuery())
                    .await().data!!.viewer!!.unreadNotificationCount!!
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getPaginatedNotifications() = Pager(
        config = PagingConfig(pageSize = NotificationsPager.NOTIFICATIONS_PER_PAGE)
    ) {
        NotificationsPager(this)
    }.flow
}