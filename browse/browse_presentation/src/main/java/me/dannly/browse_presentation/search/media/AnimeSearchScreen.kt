package me.dannly.browse_presentation.search.media

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import me.dannly.browse_presentation.search.components.SearchBar
import me.dannly.core_ui.components.ImageCard
import me.dannly.core_ui.components.paging.PagedLazyColumn
import me.dannly.core_ui.navigation.Destination
import me.dannly.core.R

@Composable
fun AnimeSearchScreen(
    viewModel: AnimeSearchViewModel = Destination.Main.Browse.viewModel(),
    onAnimeClick: (Int) -> Unit
) {
    val items = viewModel.animeList.collectAsLazyPagingItems()
    Column {
        SearchBar(text = viewModel.query, onTextChanged = {
            viewModel.setNewQuery(it)
        }) {
            viewModel.search()
        }
        PagedLazyColumn(
            lazyPagingItems = items,
            modifier = Modifier.fillMaxSize()
        ) { searchedMedia ->
            ImageCard(
                modifier = Modifier.clickable {
                    onAnimeClick(searchedMedia?.id ?: -1)
                },
                imageURL = searchedMedia?.imageUrl,
                title = searchedMedia?.userPreferredTitle,
                content = {
                    if (searchedMedia != null) {
                        searchedMedia.averageScore?.let { averageScore ->
                            Spacer(modifier = Modifier.size(2.dp))
                            Row(modifier = Modifier.size(50.dp, 24.dp)) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = stringResource(id = R.string.score),
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center)
                                )
                                Text(
                                    text = averageScore.toString(),
                                    style = TextStyle(textAlign = TextAlign.Center),
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxSize()
                                        .wrapContentSize(Alignment.Center),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}