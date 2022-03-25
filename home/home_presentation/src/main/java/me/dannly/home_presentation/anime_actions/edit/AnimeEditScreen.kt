package me.dannly.home_presentation.anime_actions.edit

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.core_ui.util.WithOrientation
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel
import me.dannly.home_presentation.anime_actions.core.AnimeLoadFailed
import me.dannly.home_presentation.anime_actions.core.AnimeProgressBar
import me.dannly.home_presentation.anime_actions.edit.components.*

@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun AnimeEditScreen(
    animeDetailsViewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()
) {
    val cachedUserAnime = animeDetailsViewModel.state.cachedUserAnime
    val spacing = LocalSpacing.current
    AnimeLoadFailed()
    Column(Modifier.padding(spacing.spaceMedium), verticalArrangement = Arrangement.SpaceEvenly) {
        AnimeProgressBar(visible = animeDetailsViewModel.state.cachedAnime == null)
        WithOrientation(portrait = {
            AnimeEditScore(Modifier.fillMaxWidth())
            AnimeEditStatus(Modifier.fillMaxWidth())
        }) {
            Row(horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)) {
                AnimeEditScore(Modifier.weight(1f))
                AnimeEditStatus(Modifier.weight(1f))
            }
        }
        AnimeEpisodePicker(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            range = 0f..(animeDetailsViewModel.state.cachedAnime?.episodeCount?.toFloat() ?: 0f),
            textFieldText = animeDetailsViewModel.state.sliderText,
            onTextChange = {
                animeDetailsViewModel.onEditEvent(AnimeDetailsEvent.AnimeEditEvent.SetSliderText(it))
            }, sliderProgress = animeDetailsViewModel.state.sliderProgress
        ) {
            animeDetailsViewModel.onEditEvent(AnimeDetailsEvent.AnimeEditEvent.SetSliderProgress(it))
        }
        UpdateButton(cachedUserAnime = cachedUserAnime)
    }
    UpdatingDialog()
}