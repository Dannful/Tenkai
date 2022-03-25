package me.dannly.tenkaiapp.components.navigation.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.activity.ActivityScreen
import me.dannly.profile_presentation.favourites.FavouritesScreen
import me.dannly.profile_presentation.info.UserInfoScreen
import me.dannly.profile_presentation.scores.ScoresScreen

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    coroutineScope: CoroutineScope
) {
    navigation(
        startDestination = Destination.Main.Profile.Info.route.toString(), route =
        Destination.Main.Profile.route.toString()
    ) {
        composable(Destination.Main.Profile.Info.route.toString(), arguments = listOf(
            navArgument(Destination.Main.Profile.Info.route.arguments[0].name) {
                type = NavType.IntType
                defaultValue = -1
            }
        )) {
            UserInfoScreen()
        }
        composable(Destination.Main.Profile.Activity.route.toString()) {
            ActivityScreen(onListActivityClick = {
                coroutineScope.launch {
                    navController.navigate(Destination.AnimeActions.Info.route.withArguments(it.toString()))
                }
            }) {
                coroutineScope.launch {
                    navController.navigate(Destination.Main.Profile.Info.route.withArguments(it.toString()))
                }
            }
        }
        composable(Destination.Main.Profile.Favourites.route.toString()) {
            FavouritesScreen {
                coroutineScope.launch {
                    navController.navigate(Destination.AnimeActions.Info.route.withArguments(it.toString()))
                }
            }
        }
        composable(Destination.Main.Profile.Scores.route.toString()) {
            ScoresScreen()
        }
    }
}