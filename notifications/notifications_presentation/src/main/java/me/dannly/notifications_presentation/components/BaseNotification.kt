package me.dannly.notifications_presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.dannly.core_ui.util.formatDateTime
import me.dannly.core_ui.components.ImageCard
import java.time.ZoneId
import java.util.*

@Composable
fun BaseNotification(
    createdAt: Int?,
    image: String?,
    title: String?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ImageCard(
        imageURL = image,
        title = title,
        modifier = modifier
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            content()
            createdAt?.let {
                Text(
                    text = formatDateTime(
                        localDateTime = Date(it * 1000L).toInstant().atZone(ZoneId.systemDefault())
                            .toLocalDateTime()
                    ),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}