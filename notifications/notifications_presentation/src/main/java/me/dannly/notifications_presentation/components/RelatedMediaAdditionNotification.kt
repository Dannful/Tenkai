package me.dannly.notifications_presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import me.dannly.notifications_domain.model.RelatedMediaAdditionNotification
import me.dannly.core.R

@Composable
fun RelatedMediaAdditionNotification(
    relatedMediaAdditionNotification: RelatedMediaAdditionNotification?,
    onRelatedMediaAdditionNotificationClick: (Int) -> Unit
) {
    if (relatedMediaAdditionNotification == null)
        return
    BaseNotification(
        createdAt = relatedMediaAdditionNotification.createdAt,
        title = relatedMediaAdditionNotification.mediaTitle,
        image = relatedMediaAdditionNotification.mediaImage,
        modifier = if (relatedMediaAdditionNotification.isTv) Modifier.clickable {
            onRelatedMediaAdditionNotificationClick(relatedMediaAdditionNotification.mediaId)
        } else Modifier
    ) {
        Text(
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Justify,
            text = stringResource(
                id = R.string.show_added,
                relatedMediaAdditionNotification.mediaTitle
            )
        )
    }
}