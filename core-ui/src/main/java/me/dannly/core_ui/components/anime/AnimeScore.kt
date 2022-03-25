package me.dannly.core_ui.components.anime

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dannly.core.R
import me.dannly.core_ui.components.StrokedText
import me.dannly.core_ui.theme.LocalSpacing

@Composable
fun AnimeScore(
    averageScore: Number?
) {
    val spacing = LocalSpacing.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(23.dp)
            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(id = R.string.score),
                tint = Color.Yellow,
                modifier = Modifier.size(15.dp)
            )
        }
        StrokedText(
            text = averageScore.toString(),
            textColor = MaterialTheme.colors.onSurface,
            strokeColor = MaterialTheme.colors.surface,
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        )
    }
}