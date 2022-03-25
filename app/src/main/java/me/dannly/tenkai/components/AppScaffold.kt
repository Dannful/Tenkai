package me.dannly.tenkai.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import me.dannly.core_ui.navigation.Destination
import me.dannly.tenkai.components.toolbar.appToolbar
import me.dannly.tenkai.components.toolbar.defaultToolbarTitle
import me.dannly.tenkaiapp.components.drawer.drawerContent
import me.dannly.tenkaiapp.components.drawer.drawerShape

@Composable
fun AppScaffold(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    loggedIn: Boolean = true,
    bottomNavigationItems: List<Destination> = emptyList(),
    navigationRailItems: List<Destination> = emptyList(),
    title: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    if (loggedIn) {
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            drawerShape = drawerShape,
            drawerContent = drawerContent(
                drawerState = scaffoldState.drawerState
            ),
            bottomBar = bottomNavigationItems.takeIf { it.isNotEmpty() }
                ?.let { screenBottomNavigation(it) }
                ?: {}, topBar = appToolbar(drawerOpen = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }, title = title ?: defaultToolbarTitle, icon = icon, actions = actions)
        ) { paddingValues ->
            Row(Modifier.padding(paddingValues)) {
                navigationRailItems.takeIf { it.isNotEmpty() }
                    ?.let {
                        ScreenNavigationRail(it)
                    }
                content()
            }
        }
    } else {
        content()
    }
}