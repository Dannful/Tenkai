package me.dannly.home_presentation.settings.sources.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.core.R
import me.dannly.home_presentation.settings.sources.SourcesEvent
import me.dannly.home_presentation.settings.sources.SourcesViewModel

@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun AddSourceButton(
    viewModel: SourcesViewModel = hiltViewModel()
) {
    AddOrEditSourceDialog()
    Button(
        onClick = { viewModel.onEvent(SourcesEvent.SetEditDialogVisible(true)) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(id = R.string.add))
    }
}