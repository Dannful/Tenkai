package com.github.dannful.core_ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object Onboarding : Route() {

        @Serializable
        data object Login : Route()
    }

    @Serializable
    data object Home : Route() {

        @Serializable
        data object ListView : Route()

        @Serializable
        data object Search : Route()
    }

    val navRoute: String
        get() = javaClass.name.replace("$", ".")
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navHostController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentBackStackEntry = remember(this) {
        navHostController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel<T>(parentBackStackEntry)
}