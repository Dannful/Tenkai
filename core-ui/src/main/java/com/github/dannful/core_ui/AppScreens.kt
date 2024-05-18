package com.github.dannful.core_ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.github.dannful.core_ui.navigation.Route

data class AppScreen(
    val route: Route,
    val topBar: @Composable () -> Unit = {},
    val bottomBar: @Composable () -> Unit = {},
    val floatingActionButton: @Composable () -> Unit = {},
    val snackBarHost: @Composable () -> Unit = {},
    val content: @Composable ((Destination) -> Unit) -> Unit
)

data class Destination(
    val route: Route,
    val arguments: List<String> = emptyList()
)

