package com.github.dannful.media_search_presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core_ui.LocalSpacingProvider
import com.github.dannful.core_ui.R
import com.github.dannful.core_ui.components.MediaInfoModal
import com.github.dannful.core_ui.navigation.Route
import com.github.dannful.core_ui.util.UiConstants
import com.github.dannful.core_ui.util.isLoading

@Composable
private fun MediaSearchScreen(
    mediaSearchState: MediaSearchState,
    onUpdateState: (MediaSearchEvent) -> Unit,
    onSend: () -> Unit
) {
    val pagingItems = mediaSearchState.pagingData.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .padding(LocalSpacingProvider.current.medium)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchQuery(pagingItems, mediaSearchState, onUpdateState, onSend)
        StartDate(mediaSearchState, pagingItems.isLoading, onUpdateState)
        GenreRow(mediaSearchState, onUpdateState, onSend, pagingItems)
        ResultsList(
            pagingItems = pagingItems,
            onEvent = onUpdateState
        )
    }
}

@Composable
private fun StartDate(
    mediaSearchState: MediaSearchState,
    isLoading: Boolean,
    onUpdateState: (MediaSearchEvent) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(
            LocalSpacingProvider.current.small,
            Alignment.CenterHorizontally
        )
    ) {
        items(mediaSearchState.years, key = {
            it
        }) {
            InputChip(
                enabled = !isLoading,
                selected = mediaSearchState.mediaSearch.seasonYear == it,
                onClick = {
                    onUpdateState(MediaSearchEvent.ToggleStartYear(it))
                    onUpdateState(MediaSearchEvent.Send)
                },
                label = {
                    Text(text = it.toString())
                })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ResultsList(
    pagingItems: LazyPagingItems<Media>,
    onEvent: (MediaSearchEvent) -> Unit
) {
    val refreshState = rememberPullRefreshState(
        refreshing = pagingItems.isLoading,
        onRefresh = {
            pagingItems.refresh()
        })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(
                state = refreshState
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.FixedSize(UiConstants.CARD_WIDTH),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(LocalSpacingProvider.current.small),
            horizontalArrangement = Arrangement.spacedBy(
                LocalSpacingProvider.current.small,
                Alignment.CenterHorizontally
            )
        ) {
            items(count = pagingItems.itemCount, key = pagingItems.itemKey {
                it.id
            }) {
                val item = pagingItems[it]
                MediaComponent(media = item, onMediaClick = onEvent)
            }
        }
        PullRefreshIndicator(
            refreshing = pagingItems.isLoading,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun GenreRow(
    mediaSearchState: MediaSearchState,
    onUpdateState: (MediaSearchEvent) -> Unit,
    onSend: () -> Unit,
    pagingItems: LazyPagingItems<Media>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(
            LocalSpacingProvider.current.small,
            Alignment.CenterHorizontally
        )
    ) {
        items(mediaSearchState.genres, key = {
            it
        }) {
            InputChip(selected = mediaSearchState.mediaSearch.genres.contains(it), onClick = {
                onUpdateState(MediaSearchEvent.ToggleGenre(it))
                onSend()
            }, label = {
                Text(text = it)
            }, enabled = !pagingItems.isLoading)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchQuery(
    pagingItems: LazyPagingItems<Media>,
    mediaSearchState: MediaSearchState,
    onUpdateState: (MediaSearchEvent) -> Unit,
    onSend: () -> Unit
) {
    SearchBar(
        enabled = !pagingItems.isLoading,
        query = mediaSearchState.mediaSearch.query.orEmpty(),
        onQueryChange = {
            onUpdateState(MediaSearchEvent.SearchQuery(it))
        },
        onSearch = {
            onSend()
        },
        active = false,
        onActiveChange = {},
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.type_search_query))
        },
        content = {},
        modifier = Modifier.fillMaxWidth()
    )
}

fun NavGraphBuilder.searchRoute() {
    composable<Route.Home.Search> {
        val viewModel: MediaSearchViewModel = hiltViewModel()
        viewModel.state.clickedMedia?.let { mediaInfo ->
            MediaInfoModal(
                mediaInfo = mediaInfo, onDismiss = {
                    viewModel.onEvent(MediaSearchEvent.SetCurrentMedia(null))
                }, onEditInfo = {
                    viewModel.onEvent(
                        MediaSearchEvent.SetCurrentUpdate(
                            it
                        )
                    )
                }
            ) {
                viewModel.onEvent(MediaSearchEvent.SendMediaUpdate)
            }
        }
        MediaSearchScreen(
            mediaSearchState = viewModel.state,
            onUpdateState = viewModel::onEvent,
            onSend = {
                viewModel.onEvent(MediaSearchEvent.Send)
            },
        )
    }
}