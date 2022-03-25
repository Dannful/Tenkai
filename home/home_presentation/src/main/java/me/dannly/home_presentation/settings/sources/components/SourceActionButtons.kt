package me.dannly.home_presentation.settings.sources.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.core.domain.local.model.SourcedFavourite
import me.dannly.core_ui.components.dialog.DeleteConfirmationDialog
import me.dannly.home_presentation.settings.sources.SourcesEvent
import me.dannly.home_presentation.settings.sources.SourcesViewModel
import me.dannly.core.R

@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun RowScope.SourceActionButtons(
    sourcedFavourite: SourcedFavourite,
    viewModel: SourcesViewModel = hiltViewModel()
) {
    Row(modifier = Modifier.align(Alignment.CenterVertically)) {
        AddOrEditSourceDialog()
        DeleteConfirmationDialog(
            deleteDialogVisible = viewModel.state.deleteDialogVisible,
            dismiss = { viewModel.onEvent(SourcesEvent.SetDeleteDialogVisible(false)) }) {
            viewModel.onEvent(SourcesEvent.RemoveSource(sourcedFavourite.favourite))
        }
        IconButton(
            onClick = {
                viewModel.onEvent(SourcesEvent.SetEditDialogVisible(true, sourcedFavourite.favourite))
            }, modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = stringResource(id = R.string.edit)
            )
        }
        IconButton(
            onClick = {
                viewModel.onEvent(SourcesEvent.SetDeleteDialogVisible(true))
            }, modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.delete)
            )
        }
    }
}