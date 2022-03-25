package me.dannly.browse_presentation.catalog

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.dannly.browse_presentation.catalog.components.ChipsTab
import me.dannly.browse_presentation.catalog.components.SortBox
import me.dannly.core.R
import me.dannly.core.domain.remote.model.AnimeSort
import me.dannly.core_ui.components.GridImageCard
import me.dannly.core_ui.components.anime.AnimeScore
import me.dannly.core_ui.components.anime.AnimeTitle
import me.dannly.core_ui.components.paging.PagedGrid
import me.dannly.core_ui.navigation.Destination

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalCoroutinesApi::class
)
@Composable
fun CatalogScreen(
    browseAnimeViewModel: BrowseAnimeViewModel = Destination.Main.Browse.viewModel(),
    onAnimeClick: (Int) -> Unit
) {
    Column {
        var sortVisible by remember {
            mutableStateOf(false)
        }
        ChipsTab(
            items = browseAnimeViewModel.state.allGenres,
            isSelected = { browseAnimeViewModel.getLastSortAndGenres().genres.contains(it) },
            initialScrollPosition = browseAnimeViewModel.scrollPosition,
            onScrollChange = {
                browseAnimeViewModel.onEvent(
                    BrowseAnimeEvent.SetScrollPosition(
                        it
                    )
                )
            },
            onSelected = { string, selected ->
                browseAnimeViewModel.onEvent(
                    when (selected) {
                        true -> BrowseAnimeEvent.SelectGenre(string)
                        false -> BrowseAnimeEvent.UnselectGenre(string)
                    }
                )
            })
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                sortVisible = !sortVisible
            }
        ) {
            Icon(
                contentDescription = stringResource(id = R.string.sort),
                painter = painterResource(id = R.drawable.ic_baseline_sort_24)
            )
        }
        SortBox(
            visible = sortVisible, options = listOf(
                stringResource(id = R.string.popularity),
                stringResource(id = R.string.score)
            )
        ) { index, sortType ->
            browseAnimeViewModel.onEvent(
                BrowseAnimeEvent.SetSort(
                    when (index) {
                        0 -> sortType.returnIf(AnimeSort.POPULARITY, AnimeSort.POPULARITY_DESC)
                        1 -> sortType.returnIf(AnimeSort.SCORE, AnimeSort.SCORE_DESC)
                        else -> sortType.returnIf(
                            AnimeSort.TITLE_ROMAJI,
                            AnimeSort.TITLE_ROMAJI_DESC
                        )
                    }
                )
            )
        }
        PagedGrid(
            modifier = Modifier.fillMaxSize(),
            lazyPagingItems = browseAnimeViewModel.state.anime.collectAsLazyPagingItems()
        ) {
            if (it == null)
                return@PagedGrid
            GridImageCard(modifier = Modifier.clickable {
                onAnimeClick(it.id)
            }, imageURL = it.imageUrl, topLeft = {
                AnimeScore(it.averageScore)
            }) {
                AnimeTitle(title = it.userPreferredTitle)
            }
        }
    }
}