package me.dannly.tenkai.components.navigation.scaffold_items

import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel
import me.dannly.tenkai.components.toolbar.toolbarTitle

val navTitle: @Composable (() -> Unit)?
    @Composable
    get() {
        val backStackEntry =
            LocalNavigation.current.currentBackStackEntryAsState().value?.destination ?: return null
        return when (backStackEntry.parent?.route) {
            Destination.AnimeActions.route.toString() -> toolbarTitle(
                text = Destination.AnimeActions.viewModel<AnimeDetailsViewModel>().state.cachedAnime?.title.orEmpty()
            )
            else -> null
        }
    }