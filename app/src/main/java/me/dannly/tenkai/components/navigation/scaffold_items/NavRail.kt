package me.dannly.tenkai.components.navigation.scaffold_items

import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation

val navRail: List<Destination>
    @Composable
    get() {
        val backStackEntry =
            LocalNavigation.current.currentBackStackEntryAsState().value?.destination
                ?: return emptyList()
        return when (backStackEntry.parent?.route) {
            Destination.Main.Browse.Search.route.toString() -> Destination.Main.Browse.Search.children
            Destination.Main.Profile.route.toString() -> Destination.Main.Profile.children
            Destination.Main.Settings.route.toString() -> Destination.Main.Settings.children
            else -> emptyList()
        }
    }