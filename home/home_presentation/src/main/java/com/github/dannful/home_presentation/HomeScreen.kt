package com.github.dannful.home_presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.dannful.core.data.model.QueryInput
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import com.github.dannful.core_ui.LocalSpacingProvider
import com.github.dannful.core_ui.R
import com.github.dannful.core_ui.components.MediaInfoModal
import com.github.dannful.core_ui.components.isScoreValid
import com.github.dannful.core_ui.navigation.Route
import com.github.dannful.core_ui.navigation.sharedViewModel
import com.github.dannful.core_ui.util.UiConstants
import com.github.dannful.core_ui.util.domainUserStatus
import com.github.dannful.core_ui.util.isLoading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeComponent(
    refreshes: SharedFlow<Unit>,
    currentTab: Int,
    pagingItems: List<Flow<PagingData<UserMedia>>>,
    onEvent: (HomeEvent) -> Unit
) {
    val items = pagingItems[currentTab].collectAsLazyPagingItems()
    val refreshState = rememberPullRefreshState(
        refreshing = items.isLoading,
        onRefresh = {
            items.refresh()
        })
    LaunchedEffect(key1 = currentTab) {
        refreshes.collect {
            items.refresh()
        }
    }
    UserMediaComponent(
        medias = items,
        refreshState = refreshState,
        onEvent = onEvent
    )
    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {
        PullRefreshIndicator(
            refreshing = items.isLoading,
            state = refreshState
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun UserMediaComponent(
    medias: LazyPagingItems<UserMedia>,
    refreshState: PullRefreshState,
    onEvent: (HomeEvent) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = refreshState), verticalArrangement = Arrangement.spacedBy(
            LocalSpacingProvider.current.small
        ), columns = GridCells.FixedSize(UiConstants.CARD_WIDTH),
        horizontalArrangement = Arrangement.spacedBy(
            LocalSpacingProvider.current.small,
            Alignment.CenterHorizontally
        )
    ) {
        items(key = medias.itemKey { it.id }, count = medias.itemCount) { index ->
            val item = medias[index]
            UserMediaComponent(
                userMedia = item,
                onEvent = onEvent,
            )
        }
    }
}

@Composable
private fun HomeNavigationRail(
    currentTab: Int,
    onSelectTab: (Int) -> Unit,
) {
    NavigationRail(header = {}) {
        Spacer(modifier = Modifier.height(LocalSpacingProvider.current.small))
        UserMediaStatus.entries.minus(UserMediaStatus.UNKNOWN).forEachIndexed { index, status ->
            val statusName = domainUserStatus()[status.ordinal]
            NavigationRailItem(selected = currentTab == index, onClick = {
                onSelectTab(index)
            }, icon = {
                Icon(
                    imageVector = when (status) {
                        UserMediaStatus.CURRENT -> Icons.Default.PlayArrow
                        UserMediaStatus.PLANNING -> Icons.Default.DateRange
                        UserMediaStatus.COMPLETED -> Icons.Default.Check
                        UserMediaStatus.DROPPED -> Icons.Default.Clear
                        UserMediaStatus.PAUSED -> Icons.Default.Lock
                        UserMediaStatus.REPEATING -> Icons.Default.Refresh
                        UserMediaStatus.UNKNOWN -> return@NavigationRailItem
                    },
                    contentDescription = statusName
                )
            }, label = {
                Text(text = statusName)
            })
        }
    }
}

@Composable
fun HomeStatusNavigation(
    navHostController: NavHostController
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val viewModel =
        navBackStackEntry?.sharedViewModel<HomeViewModel>(navHostController = navHostController)
            ?: hiltViewModel()
    HomeNavigationRail(currentTab = viewModel.state.selectedTab) {
        viewModel.onEvent(HomeEvent.SetCurrentTab(it))
    }
}

@Composable
fun HomeFloatingActionButton(navHostController: NavHostController) {
    FloatingActionButton(onClick = {
        navHostController.navigate(Route.Home.Search)
    }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search)
        )
    }
}

fun NavGraphBuilder.homeSection(navHostController: NavHostController) {
    composable<Route.Home.ListView> {
        val homeViewModel =
            it.sharedViewModel<HomeViewModel>(navHostController = navHostController)
        homeViewModel.state.dialogMedia?.let { mediaInfo ->
            MediaInfoModal(mediaInfo = mediaInfo, onDismiss = {
                homeViewModel.onEvent(HomeEvent.HideDialog)
            },
                onEditInfo = { userMediaUpdate ->
                    homeViewModel.onEvent(
                        HomeEvent.UpdateDialogEditInfo(
                            userMediaUpdate
                        )
                    )
                }, onSend = {
                    homeViewModel.onEvent(
                        HomeEvent.UpdateMediaList(
                            homeViewModel.state.dialogMedia?.mediaUpdate
                                ?: return@MediaInfoModal
                        )
                    )
                })
        }
        homeViewModel.state.swipedMediaUpdate?.let { swipedMediaState ->
            ConfirmScoreComponent(
                userScoreFormat = homeViewModel.state.userScoreFormat,
                score = swipedMediaState.score.value ?: 0.0,
                onDismiss = {
                    homeViewModel.onEvent(HomeEvent.HideScoreAlert)
                }, onSend = {
                    homeViewModel.state.swipedMediaUpdate?.let { update ->
                        homeViewModel.onEvent(
                            HomeEvent.UpdateMediaList(
                                update.copy(
                                    score = QueryInput.present(
                                        it
                                    )
                                )
                            )
                        )
                    }
                })
        }
        HomeComponent(
            currentTab = homeViewModel.state.selectedTab,
            pagingItems = homeViewModel.state.pagingData,
            onEvent = homeViewModel::onEvent,
            refreshes = homeViewModel.uiRefreshes
        )
    }
}

@Composable
private fun ConfirmScoreComponent(
    userScoreFormat: UserScoreFormat,
    score: Double,
    onDismiss: () -> Unit,
    onSend: (Double) -> Unit
) {
    var scoreText by rememberSaveable {
        mutableStateOf("")
    }
    AlertDialog(title = {
        Text(text = stringResource(id = R.string.score))
    }, onDismissRequest = {
        onDismiss()
        onSend(score)
    }, confirmButton = {
        Button(enabled = isScoreValid(scoreText, userScoreFormat), onClick = {
            onSend(scoreText.toDouble())
            onDismiss()
        }) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }, text = {
        OutlinedTextField(value = scoreText, onValueChange = {
            scoreText = it
        }, isError = !isScoreValid(scoreText, userScoreFormat), label = {
            Text(text = stringResource(id = R.string.score))
        })
    })
}