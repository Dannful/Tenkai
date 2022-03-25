package me.dannly.home_presentation.user_list.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import me.dannly.core.R
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.core.util.SwipeDirection
import me.dannly.home_presentation.util.containsScore
import me.dannly.core_ui.components.dialog.InputDialog
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_domain.model.UserAnimeUpdate
import me.dannly.home_presentation.user_list.UserAnimesEvent
import me.dannly.home_presentation.user_list.UserAnimesViewModel
import me.dannly.home_presentation.user_list.util.getNewProgress
import me.dannly.home_presentation.user_list.util.updateProgress

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InputScoreDialog(
    viewModel: UserAnimesViewModel = hiltViewModel(),
    cachedUserAnime: CachedUserAnime,
    swipeableState: SwipeableState<SwipeDirection>,
    lazyPagingItems: LazyPagingItems<CachedUserAnime>,
    dialogVisible: Boolean,
    score: String,
    onScoreChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val onDone: () -> Unit = {
        focusManager.clearFocus()
        onDismiss()
        val newScore = score.toDoubleOrNull()
        if (newScore == null)
            updateProgress(
                viewModel,
                cachedUserAnime,
                swipeableState,
                lazyPagingItems
            )
        else
            viewModel.onEvent(
                UserAnimesEvent.Update(
                    UserAnimeUpdate(
                        mediaId = cachedUserAnime.cachedAnime.id,
                        score = newScore,
                        progress = getNewProgress(swipeableState, cachedUserAnime),
                        mediaListStatus = UserAnimeStatus.COMPLETED.name
                    )
                ) {
                    lazyPagingItems.refresh()
                }
            )
    }
    val notError = score.toDoubleOrNull() != null && viewModel.state.cachedScoreFormat?.containsScore(score.toDouble()) == true
    InputDialog(
        done = onDone,
        okButtonEnabled = notError,
        title = stringResource(id = R.string.set_score),
        visible = dialogVisible,
        dismiss = onDismiss
    ) {
        TextField(
            placeholder = {
                Text(text = stringResource(id = R.string.insert_score))
            }, leadingIcon = {
                Icon(imageVector = Icons.Filled.Star, contentDescription = null)
            }, colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
            isError = !notError,
            value = score,
            onValueChange = onScoreChange,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = {
                if (score.toDoubleOrNull() != null)
                    onDone()
            })
        )
    }
}