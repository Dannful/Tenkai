package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import me.dannly.core.R
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel
import me.dannly.home_presentation.util.containsScore

@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun AnimeEditScore(
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = viewModel.state.score.orEmpty(),
        onValueChange = {
            viewModel.onEditEvent(AnimeDetailsEvent.AnimeEditEvent.SetScore(it.filter { char -> char.isDigit() || char == '.' }))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus(true)
        }),
        isError = viewModel.state.score.orEmpty()
            .toDoubleOrNull() == null || viewModel.state.cachedScoreFormat?.containsScore(
            viewModel.state.score?.toDouble() ?: (-1).toDouble()
        ) == false,
        label = {
            Text(text = stringResource(id = R.string.score))
        }
    )
}