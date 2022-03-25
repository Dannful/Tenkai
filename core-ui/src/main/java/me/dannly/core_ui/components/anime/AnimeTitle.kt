package me.dannly.core_ui.components.anime

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import me.dannly.core_ui.theme.LocalSpacing

@Composable
fun AnimeTitle(title: String?) {
    val spacing = LocalSpacing.current
    Text(
        text = title.orEmpty(),
        style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center),
        modifier = Modifier
            .padding(horizontal = spacing.spaceSmall),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}