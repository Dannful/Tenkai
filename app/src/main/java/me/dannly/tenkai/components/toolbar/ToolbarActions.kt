package me.dannly.tenkai.components.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.tenkai.components.action_bar.NotificationsButton
import me.dannly.tenkai.components.action_bar.SettingsButton
import me.dannly.core_ui.navigation.Destination

fun getDefaultToolbarActions(unreadNotificationCount: Int = 0): @Composable RowScope.() -> Unit = {
    val navController = LocalNavigation.current
    NotificationsButton(unreadNotificationCount) {
        navController.navigate(Destination.Main.Notifications.route.toString())
    }
    SettingsButton {
        navController.navigate(Destination.Main.Settings.route.toString())
    }
}