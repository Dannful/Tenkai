package me.dannly.home_presentation.anime_actions.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.core_ui.util.WithOrientation
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel
import me.dannly.home_presentation.anime_actions.core.AnimeLoadFailed
import me.dannly.home_presentation.anime_actions.core.AnimeProgressBar
import me.dannly.home_presentation.anime_actions.info.components.InfoBanner
import me.dannly.home_presentation.anime_actions.info.components.InfoGenres
import me.dannly.home_presentation.anime_actions.info.components.InfoScore
import me.dannly.home_presentation.anime_actions.info.components.InfoSynopsis

@Composable
fun AnimeInfoScreen(
    animeDetailsViewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()
) {
    val cachedAnime = animeDetailsViewModel.state.cachedAnime
    val spacing = LocalSpacing.current
    AnimeLoadFailed()
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        AnimeProgressBar(visible = cachedAnime == null)
        WithOrientation(portrait = {
            InfoBanner(Modifier.weight(1f), cachedAnime)
            Column(
                Modifier
                    .weight(2f)
                    .padding(spacing.spaceSmall)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
            ) {
                InfoSynopsis(cachedAnime = cachedAnime)
                InfoGenres(cachedAnime)
                InfoScore(cachedAnime)
            }
        }) {
            Row {
                InfoBanner(Modifier.weight(1f), cachedAnime)
                Column(
                    Modifier
                        .weight(2f)
                        .padding(spacing.spaceSmall)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top,
                ) {
                    InfoSynopsis(cachedAnime = cachedAnime)
                    InfoGenres(cachedAnime)
                    InfoScore(cachedAnime)
                }
            }
        }
    }
}