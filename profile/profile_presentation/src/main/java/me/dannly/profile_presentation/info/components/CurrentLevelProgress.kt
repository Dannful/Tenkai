package me.dannly.profile_presentation.info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import me.dannly.profile_presentation.info.util.WeebLevel

@Composable
fun CurrentLevelProgress(
    minutesWatched: Int,
    level: WeebLevel
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "$minutesWatched/${if (level.requiredExperience.last != Int.MAX_VALUE) level.requiredExperience.last else level.requiredExperience.first}",
        style = MaterialTheme.typography.h6.copy(textAlign = TextAlign.Center)
    )
}