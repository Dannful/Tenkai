package me.dannly.browse_presentation.search.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import me.dannly.browse_presentation.search.components.SearchBar
import me.dannly.core.R
import me.dannly.core_ui.components.ImageCard
import me.dannly.core_ui.components.paging.PagedLazyColumn
import me.dannly.core_ui.navigation.Destination
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun UserSearchScreen(
    viewModel: UserSearchViewModel = Destination.Main.Browse.viewModel(),
    onUserClick: (Int) -> Unit
) {
    val items = viewModel.users.collectAsLazyPagingItems()
    Column {
        SearchBar(text = viewModel.query, onTextChanged = {
            viewModel.onNewQuery(it)
        }) {
            viewModel.search()
        }
        PagedLazyColumn(
            lazyPagingItems = items,
            modifier = Modifier.fillMaxSize()
        ) { searchedUser ->
            ImageCard(
                modifier = Modifier.clickable {
                    onUserClick(searchedUser?.id ?: -1)
                },
                imageURL = searchedUser?.imageUrl,
                title = searchedUser?.name,
                content = {
                    if (searchedUser != null) {
                        searchedUser.createdAt?.let { createdAt ->
                            Text(
                                text = stringResource(
                                    id = R.string.created_at, Instant.ofEpochSecond(createdAt.toLong()).atZone(
                                        ZoneId.systemDefault()
                                    ).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                                )
                            )
                        }
                    }
                }
            )
        }
    }
}