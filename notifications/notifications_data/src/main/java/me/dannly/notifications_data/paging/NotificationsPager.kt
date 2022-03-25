package me.dannly.notifications_data.paging

import me.dannly.core.data.paging.BasePagingSource
import me.dannly.notifications_domain.model.RetrievedNotification
import me.dannly.notifications_domain.repository.NotificationsRepository

class NotificationsPager(
    private val notificationsRepository: NotificationsRepository
) : BasePagingSource<RetrievedNotification>() {

    override suspend fun loadData(page: Int): List<RetrievedNotification> =
        notificationsRepository.retrieveNotifications(page).getOrThrow()

    override val perPage: Int
        get() = NOTIFICATIONS_PER_PAGE

    companion object {

        const val NOTIFICATIONS_PER_PAGE = 10
    }
}