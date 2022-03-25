package me.dannly.home_presentation.anime_actions.watch.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import me.dannly.core.R
import me.dannly.core.domain.local.model.Source
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun WatchAddSourceIcon(
    viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel(),
    dismissMenu: () -> Unit, animeId: Int
) {
    IconButton(
        onClick = {
            dismissMenu()
            viewModel.state.currentUrl?.let { currentUrl ->
                viewModel.currentlySelectedSource?.favourite?.name?.let {
                    Source(
                        animeId = animeId,
                        url = currentUrl,
                        favouriteSourceName = it
                    )
                }
            }?.let { AnimeDetailsEvent.AnimeWatchEvent.AddSource(it) }
                ?.let { viewModel.onWatchEvent(it) }
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_save_24),
            contentDescription = stringResource(id = R.string.add)
        )
    }
}