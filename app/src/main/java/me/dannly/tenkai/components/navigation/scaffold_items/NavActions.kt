package me.dannly.tenkai.components.navigation.scaffold_items

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import me.dannly.browse_presentation.catalog.components.browseNavActions
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.home_presentation.anime_actions.edit.components.animeEditNavActions
import me.dannly.home_presentation.anime_actions.info.components.animeInfoNavActions
import me.dannly.tenkai.components.toolbar.getDefaultToolbarActions

@Composable
fun getNavActions(unreadNotificationsCount: Int = 0): (@Composable RowScope.() -> Unit)? {
    val backStackEntry =
        LocalNavigation.current.currentBackStackEntryAsState().value?.destination ?: return null
    return when (backStackEntry.route) {
        Destination.AnimeActions.Edit.route.toString() -> animeEditNavActions
        Destination.AnimeActions.Info.route.toString() -> animeInfoNavActions
        Destination.Main.Home.route.toString() -> getDefaultToolbarActions(unreadNotificationsCount)
        Destination.Main.Browse.Catalog.route.toString() -> browseNavActions
        else -> null
    }
}