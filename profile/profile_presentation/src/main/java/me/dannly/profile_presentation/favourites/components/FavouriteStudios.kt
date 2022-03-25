package me.dannly.profile_presentation.favourites.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.profile_presentation.components.ChartElement
import me.dannly.profile_presentation.components.PieChart
import me.dannly.profile_presentation.components.differentColorsList

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun FavouriteStudios(viewModel: ProfileViewModel = Destination.Main.Profile.viewModel()) {
    viewModel.state.user?.let { user ->
        val elements by produceState(initialValue = listOf()) {
            value = withContext(Dispatchers.Default) {
                val studios = user.studios
                val total = studios.sumOf { it.amount }
                val colors =
                    differentColorsList(studios.size)
                        .iterator()
                studios.associateWith {
                    it.amount.toFloat().div(total)
                }.mapKeys { it.key.name }
                    .map {
                        ChartElement(
                            it.key,
                            colors.next(),
                            it.value
                        )
                    }
            }
        }
        PieChart(elements = elements)
    }
}