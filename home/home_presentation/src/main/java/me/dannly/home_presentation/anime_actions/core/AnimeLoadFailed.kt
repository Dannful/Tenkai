package me.dannly.home_presentation.anime_actions.core

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import me.dannly.core.util.UiEvent
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun AnimeLoadFailed(animeDetailsViewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()) {
    val controller = LocalNavigation.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        animeDetailsViewModel.uiEvent.collect {
            when (it) {
                UiEvent.NavigateBack -> controller.popBackStack()
                is UiEvent.ShowToast -> Toast.makeText(context, it.uiText.asString(context), Toast.LENGTH_LONG).show()
            }
        }
    }
}