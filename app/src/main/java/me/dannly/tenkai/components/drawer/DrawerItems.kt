package me.dannly.tenkai.components.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.material.DrawerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import me.dannly.core_ui.navigation.Destination

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerItems(drawerState: DrawerState) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Destination.Main.children
            .forEach {
                val screenIcon = it.routeItem ?: return@forEach
                DrawerListItem(
                    text = screenIcon.title.asString(LocalContext.current),
                    destination = it,
                    drawerState = drawerState
                )
            }
    }
}