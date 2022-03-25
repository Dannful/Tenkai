package me.dannly.tenkaiapp.components.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.dannly.browse_presentation.catalog.CatalogScreen
import me.dannly.core_ui.navigation.Destination

fun NavGraphBuilder.browseNavGraph(navController: NavHostController, coroutineScope: CoroutineScope) {
    navigation(
        startDestination = Destination.Main.Browse.Catalog.route.toString(),
        route = Destination.Main.Browse.route.toString()
    ) {
        composable(route = Destination.Main.Browse.Catalog.route.toString()) {
            CatalogScreen {
                coroutineScope.launch {
                    val route = Destination.AnimeActions.Info.route.withArguments(it.toString())
                    navController.navigate(route)
                }
            }
        }
        searchNavGraph(navController, coroutineScope)
    }
}