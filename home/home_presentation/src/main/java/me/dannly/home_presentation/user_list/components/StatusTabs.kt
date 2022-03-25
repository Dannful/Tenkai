package me.dannly.home_presentation.user_list.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import me.dannly.core_ui.components.resourceMediaListStatuses

@OptIn(
    ExperimentalPagerApi::class
)
@Composable
fun Tabs(pagerState: PagerState, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = {
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(
                        pagerState,
                        it
                    )
                    .fillMaxWidth()
            )
        }, modifier = modifier
            .fillMaxSize()
    ) {
        resourceMediaListStatuses.forEachIndexed { index, status ->
            Tab(
                text = { Text(text = status) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                })
        }
    }
}