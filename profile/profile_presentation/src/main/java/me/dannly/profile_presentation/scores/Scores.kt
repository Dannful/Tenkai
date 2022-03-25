package me.dannly.profile_presentation.scores

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.profile_presentation.components.BarChart
import me.dannly.profile_presentation.components.ChartElement
import me.dannly.profile_presentation.components.UserLoadFailed
import me.dannly.profile_presentation.components.differentColorsList

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun ScoresScreen(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel()
) {
    UserLoadFailed()
    profileViewModel.state.user?.scores?.let { scores ->
        val elements by produceState(listOf()) {
            value = withContext(Dispatchers.Default) {
                val colors = differentColorsList(scores.size).iterator()
                scores.associateWith { it.amount }.mapKeys { it.key.score.toString() }
                    .mapValues { it.value.toFloat() }
                    .map { ChartElement(it.key, colors.next(), it.value) }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(visible = profileViewModel.state.user == null) {
                CircularProgressIndicator()
            }
            BarChart(
                elements = elements
            )
        }
    }
}