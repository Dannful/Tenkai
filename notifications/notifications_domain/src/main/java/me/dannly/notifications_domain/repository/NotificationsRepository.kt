package me.dannly.notifications_domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.dannly.notifications_domain.model.RetrievedNotification

interface NotificationsRepository {

    suspend fun retrieveNotifications(page: Int, perPage: Int? = null): Result<List<RetrievedNotification>>

    suspend fun retrieveUnreadNotificationCount(): Result<Int>

    fun getPaginatedNotifications(): Flow<PagingData<RetrievedNotification>>
}