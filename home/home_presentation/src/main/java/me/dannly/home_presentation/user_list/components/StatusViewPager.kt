package me.dannly.home_presentation.user_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.core.util.SwipeDirection
import me.dannly.core_ui.components.paging.PagedGrid
import me.dannly.core_ui.components.paging.PagedLazyColumn
import me.dannly.core_ui.components.resourceMediaListStatuses
import me.dannly.home_presentation.user_list.UserAnimesViewModel

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun StatusViewPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    viewModel: UserAnimesViewModel = hiltViewModel(),
    onAnimeClick: (Int) -> Unit
) {
    HorizontalPager(
        count = resourceMediaListStatuses.size,
        state = pagerState,
        modifier = modifier
            .fillMaxSize()
    ) { pageIndex ->
        val items =
            viewModel.state.animeList.getOrDefault(UserAnimeStatus.values()[pageIndex], emptyFlow())
                .collectAsLazyPagingItems()
        Column {
            val isGrid = viewModel.isGridFlow.collectAsState(
                initial = false,
                Dispatchers.IO
            ).value ?: false
            if (!isGrid) {
                PagedLazyColumn(
                    modifier = Modifier.weight(10f),
                    lazyPagingItems = items,
                    key = {
                        it.id
                    }) {
                    val swipeState =
                        rememberSwipeableState(initialValue = SwipeDirection.INITIAL)
                    AnimeCardWithEvents(
                        cachedUserAnime = it,
                        swipeState = swipeState,
                        isGrid = isGrid,
                        onClick = onAnimeClick
                    )
                    CardSwipe(
                        cachedUserAnime = it,
                        swipeableState = swipeState,
                        lazyPagingItems = items,
                        viewModel = viewModel
                    )
                }
            } else {
                PagedGrid(
                    modifier = Modifier.weight(10f),
                    lazyPagingItems = items,
                    key = {
                        it.id
                    }
                ) {
                    val swipeState =
                        rememberSwipeableState(initialValue = SwipeDirection.INITIAL)
                    AnimeCardWithEvents(
                        cachedUserAnime = it,
                        swipeState = swipeState,
                        isGrid = isGrid,
                        onClick = onAnimeClick
                    )
                    CardSwipe(
                        cachedUserAnime = it,
                        swipeableState = swipeState,
                        lazyPagingItems = items,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}