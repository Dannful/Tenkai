package me.dannly.home_presentation.anime_actions.watch

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.core_ui.util.WithOrientation
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel
import me.dannly.home_presentation.anime_actions.core.AnimeLoadFailed
import me.dannly.home_presentation.anime_actions.watch.components.WatchBrowser
import me.dannly.home_presentation.anime_actions.watch.components.WatchCastButton
import me.dannly.home_presentation.anime_actions.watch.components.WatchDropbox


@OptIn(
    ExperimentalMaterialApi::class
)
@Composable
fun AnimeWatchScreen(
    animeDetailsViewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()
) {
    val cachedAnime = animeDetailsViewModel.state.cachedAnime
    val spacing = LocalSpacing.current
    AnimeLoadFailed()
    Column(
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        WithOrientation(portrait = {
            WatchCastButton()
            WatchBrowser(Modifier.weight(8f), animeId = cachedAnime?.id ?: -1)
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                if (cachedAnime != null) {
                    WatchDropbox(animeId = cachedAnime.id)
                }
            }
        }) {
            Row {
                Column(
                    modifier = Modifier.fillMaxWidth(0.25f),
                    verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
                ) {
                    WatchCastButton()
                    if (cachedAnime != null) {
                        WatchDropbox(animeId = cachedAnime.id)
                    }
                }
                WatchBrowser(animeId = cachedAnime?.id ?: -1)
            }
        }
    }
}