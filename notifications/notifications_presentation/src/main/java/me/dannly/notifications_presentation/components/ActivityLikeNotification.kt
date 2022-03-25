package me.dannly.notifications_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import me.dannly.notifications_domain.model.ActivityLikeNotification
import me.dannly.core.R

@Composable
fun ActivityLikeNotification(
    activityLikeNotification: ActivityLikeNotification?,
    onActivityLikeNotificationClick: (Int) -> Unit
) {
    if (activityLikeNotification == null)
        return
    BaseNotification(
        createdAt = activityLikeNotification.createdAt,
        image = activityLikeNotification.userAvatar,
        title = activityLikeNotification.userName,
        modifier = Modifier.clickable {
            onActivityLikeNotificationClick(activityLikeNotification.userId)
        }
    ) {
        Text(
            text = stringResource(
                id = R.string.user_liked,
                activityLikeNotification.userName
            ),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Justify
        )
    }
}