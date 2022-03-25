package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import me.dannly.core_ui.components.dialog.DeleteConfirmationDialog
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun AnimeEditDeleteEntry(
    visible: Boolean,
    viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel(),
    onDismiss: () -> Unit
) {
    val navController = LocalNavigation.current
    val returnToMainScreenPreference =
        viewModel.returnToMainScreen.collectAsState(true).value ?: true
    DeleteConfirmationDialog(deleteDialogVisible = visible, dismiss = onDismiss) {
        viewModel.state.cachedUserAnime?.id?.let {
            AnimeDetailsEvent.AnimeEditEvent.DeleteUserAnime(
                it
            )
        }?.let {
            if (returnToMainScreenPreference)
                navController.navigate(Destination.Main.route.toString())
            viewModel.onEditEvent(it)
            viewModel.resetUserAnime()
        }
    }
}