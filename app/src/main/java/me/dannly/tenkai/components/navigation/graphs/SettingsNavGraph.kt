package me.dannly.tenkaiapp.components.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.dannly.core.domain.preferences.Preferences
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.settings.general.GeneralSettings
import me.dannly.home_presentation.settings.sources.SourcesScreen

fun NavGraphBuilder.settingsNavGraph(preferences: Preferences) {
    navigation(
        startDestination = Destination.Main.Settings.General.route.toString(),
        route = Destination.Main.Settings.route.toString()
    ) {
        composable(Destination.Main.Settings.General.route.toString()) {
            GeneralSettings(preferences = preferences)
        }
        composable(Destination.Main.Settings.Sources.route.toString()) {
            SourcesScreen()
        }
    }
}