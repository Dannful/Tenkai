package me.dannly.tenkai.components

import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dannly.core_ui.components.DrawableOrVectorIcon
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.core_ui.navigation.Destination

@Composable
fun ScreenNavigationRail(
    items: List<Destination>
) {
    val navController = LocalNavigation.current
    NavigationRail {
        items.forEach { destination ->
            val route = destination.route.withArgumentsState()
            NavigationRailItem(
                selected = destination.isCurrentDestination,
                onClick = {
                    if (route != null) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    destination.routeItem?.let {
                        DrawableOrVectorIcon(
                            contentDescription = null,
                            drawable = it.drawable,
                            imageVector = it.imageVector
                        )
                    }
                },
                label = {
                    destination.routeItem?.title?.let {
                        Text(text = it.asString(LocalContext.current))
                    }
                })
        }
    }
}