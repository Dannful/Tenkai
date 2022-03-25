package me.dannly.profile_presentation.activity.components

import androidx.compose.foundation.clickable
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import me.dannly.core_ui.components.ImageCard
import me.dannly.profile_domain.model.activity.MessageActivity
import me.dannly.profile_domain.model.activity.RetrievedActivity
import me.dannly.profile_presentation.activity.util.SwipeDirection

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageActivity(
    messageActivity: MessageActivity?,
    lazyPagingItems: LazyPagingItems<RetrievedActivity>,
    onMessageActivityClick: (Int) -> Unit
) {
    if (messageActivity != null) {
        val swipeableState = rememberSwipeableState(initialValue = SwipeDirection.CENTER)
        ActivityDelete(
            swipeableState = swipeableState,
            activityId = messageActivity.id,
            lazyPagingItems = lazyPagingItems
        )
        ImageCard(
            rowModifier = Modifier.getSwipeableModifier(swipeableState),
            modifier = Modifier.clickable {
                onMessageActivityClick(messageActivity.senderId)
            },
            imageURL = messageActivity.senderImage,
            title = messageActivity.senderName
        ) {
            Text(text = messageActivity.message)
        }
    }
}