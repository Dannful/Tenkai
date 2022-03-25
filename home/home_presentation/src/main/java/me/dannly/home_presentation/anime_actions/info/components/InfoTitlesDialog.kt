package me.dannly.home_presentation.anime_actions.info.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R
import me.dannly.core_ui.components.dialog.InputDialog
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun InfoTitlesDialog(
    visible: Boolean,
    animeDetailsViewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel(),
    onDismiss: () -> Unit
) {
    val anime = animeDetailsViewModel.state.cachedAnime ?: return
    InputDialog(
        title = stringResource(id = R.string.alternative_titles),
        visible = visible,
        dismiss = onDismiss
    ) {
        Text(
            text = anime.synonyms.joinToString("\n") { "• $it" }
        )
    }
}