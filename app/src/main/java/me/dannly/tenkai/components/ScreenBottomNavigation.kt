package me.dannly.tenkai.components

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dannly.core_ui.components.DrawableOrVectorIcon
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.core_ui.navigation.Destination

fun screenBottomNavigation(
    items: List<Destination>
) = @Composable {
    val controller = LocalNavigation.current
    BottomAppBar {
        items.forEach { destination ->
            val route = destination.route.withArgumentsState()
            BottomNavigationItem(
                selected = destination.isCurrentDestination,
                onClick = {
                    if (route != null) {
                        controller.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }, icon = {
                    destination.routeItem?.let {
                        DrawableOrVectorIcon(
                            contentDescription = null,
                            drawable = it.drawable,
                            imageVector = it.imageVector
                        )
                    }
                }, label = {
                    destination.routeItem?.title?.let {
                        Text(text = it.asString(LocalContext.current))
                    }
                })
        }
    }
}