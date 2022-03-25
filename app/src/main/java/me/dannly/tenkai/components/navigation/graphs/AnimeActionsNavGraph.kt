package me.dannly.tenkai.components.navigation.graphs

import androidx.navigation.*
import androidx.navigation.compose.composable
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.edit.AnimeEditScreen
import me.dannly.home_presentation.anime_actions.info.AnimeInfoScreen
import me.dannly.home_presentation.anime_actions.watch.AnimeWatchScreen

fun NavGraphBuilder.animeActionsNavGraph() {
    navigation(
        startDestination = Destination.AnimeActions.Info.route.toString(),
        route = Destination.AnimeActions.route.toString()
    ) {
        composable(
            route = Destination.AnimeActions.Info.route.toString(),
            arguments = listOf(
                navArgument(
                    Destination.AnimeActions.Info.route.arguments[0].name
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            ), deepLinks = listOf(
                navDeepLink {
                    uriPattern = Destination.AnimeActions.Info.route.deepLink
                }
            )
        ) {
            AnimeInfoScreen()
        }
        composable(
            route = Destination.AnimeActions.Edit.route.toString(),
            arguments = listOf(
                navArgument(
                    Destination.AnimeActions.Edit.route.arguments[0].name
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AnimeEditScreen()
        }
        composable(
            route = Destination.AnimeActions.Watch.route.toString(),
            arguments = listOf(
                navArgument(
                    Destination.AnimeActions.Watch.route.arguments[0].name
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AnimeWatchScreen()
        }
    }
}