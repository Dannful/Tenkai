package me.dannly.home_presentation.user_list.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import me.dannly.core.util.SwipeDirection
import me.dannly.core_ui.components.paging.loading
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_presentation.user_list.UserAnimesEvent
import me.dannly.home_presentation.user_list.UserAnimesViewModel
import me.dannly.home_presentation.user_list.util.getNewProgress
import me.dannly.home_presentation.user_list.util.updateProgress

@OptIn(
    ExperimentalMaterialApi::class
)
@Composable
fun CardSwipe(
    cachedUserAnime: CachedUserAnime?,
    swipeableState: SwipeableState<SwipeDirection>,
    lazyPagingItems: LazyPagingItems<CachedUserAnime>,
    viewModel: UserAnimesViewModel = hiltViewModel()
) {
    if (cachedUserAnime != null) {
        InputScoreDialog(
            viewModel = viewModel,
            cachedUserAnime = cachedUserAnime,
            swipeableState = swipeableState,
            lazyPagingItems = lazyPagingItems,
            dialogVisible = viewModel.state.dialogVisible,
            onScoreChange = {
                viewModel.onEvent(UserAnimesEvent.SetDialogScore(it))
            }, score = viewModel.state.score
        ) {
            updateProgress(
                viewModel, cachedUserAnime, swipeableState, lazyPagingItems
            )
            viewModel.onEvent(UserAnimesEvent.SetDialogVisible(false))
        }
        LaunchedEffect(swipeableState.currentValue) {
            when (swipeableState.currentValue) {
                SwipeDirection.INITIAL -> {}
                SwipeDirection.LEFT, SwipeDirection.RIGHT -> {
                    if (lazyPagingItems.loading) {
                        swipeableState.animateTo(SwipeDirection.INITIAL)
                        return@LaunchedEffect
                    }
                    val progress = cachedUserAnime.progress
                    if ((swipeableState.currentValue == SwipeDirection.LEFT && progress.minus(
                            1
                        ) < 0) || (swipeableState.currentValue == SwipeDirection.RIGHT && progress.plus(
                            1
                        ) > cachedUserAnime.cachedAnime.episodeCount)
                    ) {
                        swipeableState.animateTo(SwipeDirection.INITIAL)
                        return@LaunchedEffect
                    }
                    if (getNewProgress(
                            swipeableState,
                            cachedUserAnime
                        ) == cachedUserAnime.cachedAnime.episodes
                    )
                        viewModel.onEvent(UserAnimesEvent.SetDialogVisible(true))
                    else
                        updateProgress(
                            viewModel,
                            cachedUserAnime,
                            swipeableState,
                            lazyPagingItems,
                        )
                    swipeableState.animateTo(SwipeDirection.INITIAL)
                }
            }
        }
    }
}