package me.dannly.notifications_presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import me.dannly.core_ui.components.paging.PagedLazyColumn
import me.dannly.notifications_domain.model.RelatedMediaAdditionNotification
import me.dannly.notifications_presentation.components.ActivityLikeNotification
import me.dannly.notifications_presentation.components.AiringNotification
import me.dannly.notifications_presentation.components.RelatedMediaAdditionNotification
import java.util.*

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = hiltViewModel(),
    onAiringNotificationClick: (Int) -> Unit,
    onRelatedMediaAdditionNotificationClick: (Int) -> Unit,
    onActivityLikeNotificationClick: (Int) -> Unit
) {
    val items = remember { viewModel.notifications }.collectAsLazyPagingItems()
    PagedLazyColumn(lazyPagingItems = items, modifier = Modifier.fillMaxSize()) {
        if (it != null) {
            AiringNotification(
                airingNotification = it.airingNotification,
                onAiringNotificationClick = onAiringNotificationClick
            )
            RelatedMediaAdditionNotification(
                relatedMediaAdditionNotification = it.relatedMediaAdditionNotification,
                onRelatedMediaAdditionNotificationClick = onRelatedMediaAdditionNotificationClick
            )
            ActivityLikeNotification(
                activityLikeNotification = it.activityLikeNotification,
                onActivityLikeNotificationClick = onActivityLikeNotificationClick
            )
        }
    }
}