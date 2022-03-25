package me.dannly.profile_presentation.favourites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.profile_presentation.components.UserLoadFailed
import me.dannly.profile_presentation.favourites.components.FavouriteGenres
import me.dannly.profile_presentation.favourites.components.FavouriteShows
import me.dannly.profile_presentation.favourites.components.FavouriteStudios
import me.dannly.profile_presentation.favourites.components.pages

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalPagerApi::class
)
@Composable
fun FavouritesScreen(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel(),
    onShowClick: (Int) -> Unit
) {
    UserLoadFailed()
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    Column {
        TabRow(selectedTabIndex = pagerState.currentPage, indicator = {
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(
                        pagerState,
                        it
                    )
                    .fillMaxWidth()
            )
        }) {
            pages.forEachIndexed { index, tabTitle ->
                Tab(
                    text = { Text(text = tabTitle) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    })
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedVisibility(visible = profileViewModel.state.user == null) {
                    CircularProgressIndicator()
                }
                when (index) {
                    0 -> FavouriteShows(onShowClick = onShowClick)
                    1 -> FavouriteGenres()
                    2 -> FavouriteStudios()
                }
            }
        }
    }
}