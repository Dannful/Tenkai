package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun UpdatingDialog(
    viewModel: AnimeDetailsViewModel = hiltViewModel()
) {
    if (viewModel.state.updating)
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
}