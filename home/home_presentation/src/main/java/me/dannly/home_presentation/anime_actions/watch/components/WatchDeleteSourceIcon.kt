package me.dannly.home_presentation.anime_actions.watch.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun WatchDeleteSourceIcon(
    viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel(),
    animeId: Int,
    dismissMenu: () -> Unit,
) {
    val hasSource =
        viewModel.currentlySelectedSource?.sources?.any { it.animeId == animeId } == true
    if (hasSource)
        IconButton(
            onClick = {
                dismissMenu()
                viewModel.currentlySelectedSource?.let { sourcedFavourite ->
                    sourcedFavourite.sources.find { it.animeId == animeId }
                        ?.let {
                            AnimeDetailsEvent.AnimeWatchEvent.RemoveSource(
                                it
                            )
                        }?.let {
                            viewModel.onWatchEvent(
                                it
                            )
                        }
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.delete)
            )
        }
}