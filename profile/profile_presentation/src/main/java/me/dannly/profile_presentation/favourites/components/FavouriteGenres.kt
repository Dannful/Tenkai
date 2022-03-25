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
fun FavouriteGenres(
    viewModel: ProfileViewModel = Destination.Main.Profile.viewModel()
) {
    viewModel.state.user?.let { user ->
        val elements by produceState(initialValue = listOf()) {
            value = withContext(Dispatchers.Default) {
                val genres = user.favouriteGenres
                val total = genres.sumOf { it.amount }
                val colors =
                    differentColorsList(genres.size)
                        .iterator()
                genres.associateWith {
                    it.amount.toFloat() / total
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