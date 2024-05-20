package com.github.dannful.media_search_presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(LocalSpacingProvider.current.small)
    ) {
        SearchQuery(pagingItems, mediaSearchState, onUpdateState, onSend)
        StartDate(mediaSearchState, pagingItems.isLoading, onUpdateState)
        MultipleChoiceRow(
            choices = mediaSearchState.genres,
            selected = mediaSearchState.mediaSearch.genres,
            enabled = !pagingItems.isLoading,
            onUpdateState = {
                onUpdateState(MediaSearchEvent.ToggleGenre(it))
                onUpdateState(MediaSearchEvent.Send)
            }
        )
        TagSearch(
            query = mediaSearchState.tagSearch,
            tags = mediaSearchState.filteredTags,
            selected = mediaSearchState.mediaSearch.tags,
            enabled = !pagingItems.isLoading,
            active = mediaSearchState.tagSearchActive,
            onActiveChange = {
                onUpdateState(MediaSearchEvent.ToggleTagSearch(it))
            },
            onQueryChange = {
                onUpdateState(MediaSearchEvent.SetTagQuery(it))
            },
            onUpdateState = {
                onUpdateState(MediaSearchEvent.ToggleTag(it))
                onUpdateState(MediaSearchEvent.Send)
            }
        )
        ResultsList(
            pagingItems = pagingItems,
            onEvent = onUpdateState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TagSearch(
    query: String,
    tags: List<String>,
    selected: Set<String>,
    enabled: Boolean,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onQueryChange: (String) -> Unit,
    onUpdateState: (String) -> Unit
) {
    DockedSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onQueryChange,
        enabled = enabled,
        onActiveChange = onActiveChange,
        active = active,
        placeholder = {
            Text(text = stringResource(id = R.string.search_tags))
        },
        leadingIcon = {
            if(active) {
                IconButton(onClick = {
                    onActiveChange(false)
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(id = R.string.close))
                }
            } else {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(LocalSpacingProvider.current.small),
            contentPadding = PaddingValues(LocalSpacingProvider.current.small)
        ) {
            items(tags, key = { it }) {
                Text(text = it, modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = enabled) {
                        onUpdateState(it)
                        onActiveChange(false)
                        onQueryChange("")
                    }
                    .padding(LocalSpacingProvider.current.small))
            }
        }
    }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(
            LocalSpacingProvider.current.small,
            Alignment.CenterHorizontally
        )
    ) {
        items(selected.toList()) {
            AssistChip(onClick = {}, label = {
                Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Text(
                        text = it,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }, trailingIcon = {
                IconButton(onClick = {
                    onUpdateState(it)
                }, enabled = enabled) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }
            }, modifier = Modifier.height(UiConstants.CHIP_HEIGHT))
        }
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
private fun MultipleChoiceRow(
    choices: List<String>,
    selected: Set<String>,
    enabled: Boolean,
    onUpdateState: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(
            LocalSpacingProvider.current.small,
            Alignment.CenterHorizontally
        )
    ) {
        items(choices, key = {
            it
        }) {
            InputChip(selected = it in selected, onClick = {
                onUpdateState(it)
            }, label = {
                Text(text = it)
            }, enabled = enabled)
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