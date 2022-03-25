package me.dannly.profile_presentation.info.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.profile_presentation.components.HalfCircularProgressIndicator
import me.dannly.profile_presentation.info.util.WeebLevel

@Composable
fun CurrentWeebLevel(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel(),
    onShowDialog: () -> Unit
) {
    profileViewModel.state.user?.minutesWatched?.let { minutesWatched ->
        val level =
            WeebLevel.values().find { minutesWatched in it.requiredExperience } ?: return@let
        CurrentWeebLevelText(weebLevel = level, onDialog = onShowDialog)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HalfCircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f),
                indicatorValue = if (level.requiredExperience.last != Int.MAX_VALUE) minutesWatched.toFloat() / level.requiredExperience.last.plus(
                    1
                ) else 1f
            )
            CurrentLevelProgress(minutesWatched, level)
        }
    }
}