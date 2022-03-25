package me.dannly.notifications_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import me.dannly.core.R
import me.dannly.notifications_domain.model.AiringNotification

@Composable
fun AiringNotification(
    airingNotification: AiringNotification?,
    onAiringNotificationClick: (Int) -> Unit
) {
    if (airingNotification == null)
        return
    BaseNotification(
        createdAt = airingNotification.createdAt,
        title = airingNotification.mediaTitle,
        image = airingNotification.mediaImage,
        modifier = Modifier.clickable {
            onAiringNotificationClick(airingNotification.mediaId)
        }
    ) {
        Text(
            text = stringResource(
                id = R.string.new_episode,
                airingNotification.episode
            ),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Justify
        )
    }
}