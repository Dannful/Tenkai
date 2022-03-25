package me.dannly.home_presentation.anime_actions.watch.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@OptIn(
    ExperimentalMaterialApi::class
)
@Composable
fun WatchDropbox(
    animeId: Int,
    viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()
) {
    var dropdownMenuExpanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        modifier = Modifier
            .wrapContentSize(),
        expanded = dropdownMenuExpanded,
        onExpandedChange = { dropdownMenuExpanded = it }) {
        val dismissMenu = { dropdownMenuExpanded = false }
        TextField(
            value = viewModel.currentlySelectedSource?.favourite?.name.orEmpty(),
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (viewModel.currentlySelectedSource == null)
                    return@TextField
                Row {
                    WatchAddSourceIcon(dismissMenu = dismissMenu, animeId = animeId)
                    WatchDeleteSourceIcon(
                        dismissMenu = dismissMenu,
                        animeId = animeId
                    )
                }
            }
        )
        ExposedDropdownMenu(
            expanded = dropdownMenuExpanded,
            onDismissRequest = dismissMenu
        ) {
            viewModel.state.favouriteWithSources.forEachIndexed { index, favouriteWithSource ->
                if (favouriteWithSource.favourite.name == viewModel.currentlySelectedSource?.favourite?.name)
                    return@forEachIndexed
                DropdownMenuItem(onClick = {
                    viewModel.onWatchEvent(AnimeDetailsEvent.AnimeWatchEvent.SetSourceIndex(index))
                    dropdownMenuExpanded = false
                }) {
                    Text(
                        text = favouriteWithSource.favourite.name
                    )
                }
            }
        }
    }
}