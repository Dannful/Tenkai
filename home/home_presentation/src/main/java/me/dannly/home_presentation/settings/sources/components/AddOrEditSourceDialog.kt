package me.dannly.home_presentation.settings.sources.components

import android.webkit.URLUtil
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.core.R
import me.dannly.core.domain.local.model.Favourite
import me.dannly.core_ui.components.dialog.InputDialog
import me.dannly.home_presentation.settings.sources.SourcesEvent
import me.dannly.home_presentation.settings.sources.SourcesViewModel

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun AddOrEditSourceDialog(
    viewModel: SourcesViewModel = hiltViewModel()
) {
    val sourcedFavourite = viewModel.state.currentlySelectedFavourite
    val focusRequesters = List(2) { FocusRequester() }
    val state = viewModel.state
    if (state.editDialogVisible) {
        val dismiss = {
            viewModel.onEvent(SourcesEvent.SetEditDialogVisible(false))
        }
        InputDialog(
            title = if (sourcedFavourite == null) stringResource(id = R.string.add) else stringResource(
                id = R.string.edit
            ),
            visible = state.editDialogVisible, dismiss = dismiss,
            okButtonEnabled = URLUtil.isValidUrl(state.newSourceUrl) && state.newSourceTitle.isNotBlank(),
            done = {
                dismiss()
                viewModel.onEvent(
                    SourcesEvent.AddSource(
                        Favourite(
                            name = state.newSourceTitle,
                            url = URLUtil.guessUrl(state.newSourceUrl)
                        )
                    )
                )
            }
        ) {
            OutlinedTextField(
                value = state.newSourceTitle,
                onValueChange = {
                    viewModel.onEvent(SourcesEvent.SetNewSourceTitle(it.replace("\n", "")))
                },
                singleLine = true,
                label = {
                    Text(text = stringResource(id = R.string.title))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusRequesters[1].requestFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesters[0]),
                readOnly = sourcedFavourite != null,
            )
            val focusManager = LocalFocusManager.current
            OutlinedTextField(value = state.newSourceUrl, onValueChange = {
                viewModel.onEvent(
                    SourcesEvent.SetNewSourceUrl(
                        it.replace("\n", "")
                    )
                )
            }, singleLine = true, label = {
                Text(text = "URL")
            }, modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequesters[1]),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    focusRequesters[1].freeFocus()
                    focusManager.clearFocus(true)
                })
            )
        }
    }
}