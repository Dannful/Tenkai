package me.dannly.core_ui.components.paging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyGridState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import me.dannly.core_ui.components.GRID_CELL_WIDTH
import me.dannly.core_ui.theme.LocalSpacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
inline fun <T : Any> PagedGrid(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<T>,
    state: LazyGridState = rememberLazyGridState(),
    noinline key: ((T) -> Int)? = null,
    crossinline content: @Composable ((T?) -> Unit)
) {
    val spacing = LocalSpacing.current
    PagedRefresh(
        modifier = modifier.fillMaxSize(),
        lazyPagingItems = lazyPagingItems
    ) {
        LazyVerticalGrid(
            verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
            horizontalArrangement = Arrangement.spacedBy(
                spacing.spaceSmall,
                Alignment.CenterHorizontally
            ),
            contentPadding = PaddingValues(
                spacing.spaceSmall
            ),
            cells = GridCells.Adaptive(GRID_CELL_WIDTH),
            state = state,
            modifier = Modifier.fillMaxSize()
        ) {
            items(lazyPagingItems.itemCount, key = { index ->
                key?.invoke(lazyPagingItems[index] ?: return@items index) ?: index
            }, contentType = {
                lazyPagingItems[it]
            }) { index ->
                content(lazyPagingItems[index])
            }
        }
    }
}