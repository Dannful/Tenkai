package me.dannly.tenkai.components.navigation.scaffold_items

import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

val navBottomBar: List<Destination>
    @Composable
    get() {
        val parent =
            LocalNavigation.current.currentBackStackEntryAsState().value?.destination?.parent?.route
                ?: return emptyList()
        return when (parent) {
            Destination.AnimeActions.route.toString() ->  Destination.AnimeActions.children
            else -> emptyList()
        }
    }