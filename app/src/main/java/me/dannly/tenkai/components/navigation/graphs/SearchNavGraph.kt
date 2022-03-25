package me.dannly.tenkaiapp.components.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.dannly.browse_presentation.search.media.AnimeSearchScreen
import me.dannly.browse_presentation.search.user.UserSearchScreen
import me.dannly.core_ui.navigation.Destination

fun NavGraphBuilder.searchNavGraph(navController: NavHostController, coroutineScope: CoroutineScope) {
    navigation(
        startDestination = Destination.Main.Browse.Search.Shows.route.toString(),
        route = Destination.Main.Browse.Search.route.toString()
    ) {
        composable(Destination.Main.Browse.Search.Shows.route.toString()) {
            AnimeSearchScreen {
                coroutineScope.launch {
                    navController.navigate(Destination.AnimeActions.Info.route.withArguments(it.toString()))
                }
            }
        }
        composable(Destination.Main.Browse.Search.Users.route.toString()) {
            UserSearchScreen {
                coroutineScope.launch {
                    navController.navigate(Destination.Main.Profile.Info.route.withArguments(it.toString()))
                }
            }
        }
    }
}