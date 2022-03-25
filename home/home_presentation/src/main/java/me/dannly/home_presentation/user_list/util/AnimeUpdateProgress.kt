package me.dannly.home_presentation.user_list.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.paging.compose.LazyPagingItems
import me.dannly.core.util.SwipeDirection
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_domain.model.UserAnimeUpdate
import me.dannly.home_presentation.user_list.UserAnimesEvent
import me.dannly.home_presentation.user_list.UserAnimesViewModel

@OptIn(ExperimentalMaterialApi::class)
fun updateProgress(
    viewModel: UserAnimesViewModel,
    cachedUserAnime: CachedUserAnime,
    swipeableState: SwipeableState<SwipeDirection>,
    lazyPagingItems: LazyPagingItems<CachedUserAnime>,
) {
    viewModel.onEvent(
        UserAnimesEvent.Update(
            UserAnimeUpdate(
                mediaId = cachedUserAnime.cachedAnime.id,
                progress = getNewProgress(swipeableState, cachedUserAnime),
                score = cachedUserAnime.score,
                mediaListStatus = cachedUserAnime.status.name
            )
        ) {
            lazyPagingItems.refresh()
        })
}

@OptIn(ExperimentalMaterialApi::class)
fun getNewProgress(
    swipeableState: SwipeableState<SwipeDirection>,
    cachedUserAnime: CachedUserAnime
) = if (swipeableState.currentValue == SwipeDirection.LEFT)
    cachedUserAnime.progress - 1 else cachedUserAnime.progress + 1