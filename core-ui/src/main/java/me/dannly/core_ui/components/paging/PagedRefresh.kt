package me.dannly.core_ui.components.paging

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
inline fun <T : Any> PagedRefresh(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<T>,
    crossinline content: @Composable () -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = lazyPagingItems.loading),
        onRefresh = {
            lazyPagingItems.refresh()
        }, indicator = { swipeRefreshState, distance ->
            SwipeRefreshIndicator(
                state = swipeRefreshState, refreshTriggerDistance = distance,
                scale = true, shape = MaterialTheme.shapes.small
            )
        }, modifier = modifier.fillMaxSize(), content = {
            content()
        }
    )
}