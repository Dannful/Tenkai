package me.dannly.profile_presentation.favourites.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import me.dannly.core_ui.components.GridImageCard
import me.dannly.core_ui.components.anime.AnimeScore
import me.dannly.core_ui.components.anime.AnimeTitle
import me.dannly.core_ui.components.paging.PagedGrid
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteShows(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel(),
    onShowClick: (Int) -> Unit
) {
    PagedGrid(
        modifier = Modifier.fillMaxSize(),
        lazyPagingItems = profileViewModel.state.favourites.collectAsLazyPagingItems()
    ) {
        if (it != null) {
            GridImageCard(imageURL = it.imageUrl, modifier = Modifier.clickable {
                onShowClick(it.id)
            }, topLeft = {
                AnimeScore(it.averageScore)
            }) {
                AnimeTitle(title = it.userPreferredTitle)
            }
        }
    }
}