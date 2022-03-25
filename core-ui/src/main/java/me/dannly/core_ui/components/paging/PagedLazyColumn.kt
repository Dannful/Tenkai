package me.dannly.core_ui.components.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import me.dannly.core_ui.theme.LocalSpacing

@Composable
inline fun <T : Any> PagedLazyColumn(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<T>,
    state: LazyListState = rememberLazyListState(),
    noinline key: ((T) -> Int)? = null,
    crossinline content: @Composable LazyItemScope.(T?) -> Unit
) {
    val spacing = LocalSpacing.current
    PagedRefresh(
        modifier = modifier,
        lazyPagingItems = lazyPagingItems
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall),
            contentPadding = PaddingValues(spacing.spaceSmall)
        ) {
            items(lazyPagingItems, key = key) {
                content(it)
            }
            retryButton(lazyPagingItems.error) {
                lazyPagingItems.retry()
            }
        }
    }
}