package me.dannly.notifications_presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.dannly.notifications_domain.repository.NotificationsRepository
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    notificationsRepository: NotificationsRepository,
) : ViewModel() {

    val notifications = notificationsRepository.getPaginatedNotifications()
}