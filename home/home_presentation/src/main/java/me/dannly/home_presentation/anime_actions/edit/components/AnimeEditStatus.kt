package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.dannly.core.R
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.core_ui.components.resourceMediaListStatuses
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@Composable
fun AnimeEditStatus(
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()
) {
    val statuses = resourceMediaListStatuses
    if (viewModel.state.statusPopupVisible) {
        StatusesDialog(
            current = if (viewModel.state.status?.ordinal != null) statuses[viewModel.state.status!!.ordinal] else "",
            onSelectedChange = {
                viewModel.onEditEvent(AnimeDetailsEvent.AnimeEditEvent.SetStatusPopupVisible(false))
                if (it != null)
                    viewModel.onEditEvent(
                        AnimeDetailsEvent.AnimeEditEvent.SetStatus(
                            UserAnimeStatus.values()[statuses.indexOf(
                                it
                            )]
                        )
                    )
            }
        )
    }
    OutlinedTextField(value = if (viewModel.state.status?.ordinal != null) statuses[viewModel.state.status!!.ordinal] else "",
        onValueChange = {},
        readOnly = true,
        modifier = modifier,
        interactionSource = remember { MutableInteractionSource() }.also {
            val pressed by it.collectIsPressedAsState()
            if (pressed)
                viewModel.onEditEvent(AnimeDetailsEvent.AnimeEditEvent.SetStatusPopupVisible(true))
        },
        label = {
            Text(text = stringResource(id = R.string.status))
        })
}