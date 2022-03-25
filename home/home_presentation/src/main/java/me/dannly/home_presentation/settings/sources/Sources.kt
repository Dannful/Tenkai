package me.dannly.home_presentation.settings.sources

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.home_presentation.settings.sources.components.AddSourceButton
import me.dannly.home_presentation.settings.sources.components.SourceItem

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SourcesScreen(
    sourcesViewModel: SourcesViewModel = hiltViewModel()
) {
    val items by sourcesViewModel.favourites.collectAsState(initial = listOf())
    val spacing = LocalSpacing.current
    LazyColumn(
        contentPadding = PaddingValues(spacing.spaceSmall),
        modifier = Modifier.animateContentSize()
    ) {
        items(items) {
            SourceItem(it)
        }
        item {
            AddSourceButton()
        }
    }
}