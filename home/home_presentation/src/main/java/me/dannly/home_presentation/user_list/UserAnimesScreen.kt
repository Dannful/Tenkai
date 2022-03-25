package me.dannly.home_presentation.user_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import me.dannly.core_ui.util.withOrientation
import me.dannly.home_presentation.user_list.components.StatusViewPager
import me.dannly.home_presentation.user_list.components.Tabs

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun UserAnimesScreen(
    onAnimeClick: (Int) -> Unit
) {
    val pagerState = rememberPagerState(0)
    Column {
        Tabs(
            pagerState = pagerState, modifier = Modifier.weight(
                withOrientation(
                    portrait = 1f,
                    landscape = 2f
                )
            )
        )
        StatusViewPager(
            pagerState = pagerState,
            modifier = Modifier.weight(9f),
            onAnimeClick = onAnimeClick
        )
    }
}