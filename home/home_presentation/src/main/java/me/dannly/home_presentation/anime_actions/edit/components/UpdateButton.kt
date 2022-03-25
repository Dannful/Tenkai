package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.core.R
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_domain.model.UserAnimeUpdate
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun UpdateButton(
    animeDetailsViewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel(),
    cachedUserAnime: CachedUserAnime?
) {
    val enabled =
        if (cachedUserAnime == null) {
            animeDetailsViewModel.state.status != null
        } else {
            (animeDetailsViewModel.state.status != cachedUserAnime.status || animeDetailsViewModel.state.sliderProgress?.toInt() != cachedUserAnime.progress || animeDetailsViewModel.state.score?.toDoubleOrNull() != cachedUserAnime.score)
        }
    val navController = LocalNavigation.current
    val shouldReturnToMainScreen =
        animeDetailsViewModel.returnToMainScreen.collectAsState(initial = true).value ?: true
    Button(onClick = {
        cachedUserAnime?.id?.let { animeId ->
            animeDetailsViewModel.onEditEvent(
                AnimeDetailsEvent.AnimeEditEvent.UpdateUserAnime(
                    UserAnimeUpdate(
                        mediaId = animeId,
                        mediaListStatus = animeDetailsViewModel.state.status!!.name,
                        score = animeDetailsViewModel.state.score?.toDoubleOrNull(),
                        progress = animeDetailsViewModel.state.sliderProgress!!.toInt()
                    )
                ) {
                    animeDetailsViewModel.resetUserAnime()
                    if (shouldReturnToMainScreen)
                        navController.navigate(Destination.Main.Home.route.toString())
                }
            )
        }
    }, modifier = Modifier.fillMaxWidth(), enabled = enabled) {
        Text(text = stringResource(id = R.string.save))
    }
}