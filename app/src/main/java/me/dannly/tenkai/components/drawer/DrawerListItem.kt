package me.dannly.tenkai.components.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.launch
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.core_ui.theme.LocalSpacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerListItem(
    text: String,
    destination: Destination,
    drawerState: DrawerState
) {
    val route = destination.route.withArgumentsState()
    val navController = LocalNavigation.current
    val spacing = LocalSpacing.current
    val scope = rememberCoroutineScope()
    val tint = if (destination.isCurrentDestination) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
    ListItem(text = {
        Text(
            text = text,
            color = tint,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, icon = {
        destination.routeItem?.imageVector?.let {
            Icon(
                contentDescription = null,
                imageVector = it,
                tint = tint
            )
        }
        destination.routeItem?.drawable?.let {
            Icon(
                contentDescription = null,
                painter = painterResource(id = it),
                tint = tint
            )
        }
    }, modifier = Modifier
        .padding(
            start = spacing.spaceSmall,
            end = spacing.spaceSmall,
            top = spacing.spaceSmall
        )
        .clickable {
            if(destination == Destination.Main.Home)
                navController.backQueue.clear()
            if (route != null) {
                navController.navigate(route) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
            scope.launch {
                drawerState.close()
            }
        }
        .let {
            if (destination.isCurrentDestination)
                it.background(
                    MaterialTheme.colors.primary.copy(alpha = 0.12f),
                    MaterialTheme.shapes.small
                )
            else
                it
        })
}