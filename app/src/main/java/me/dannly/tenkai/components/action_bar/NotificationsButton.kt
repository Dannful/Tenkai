package me.dannly.tenkai.components.action_bar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R

@Composable
inline fun NotificationsButton(
    unreadNotificationCount: Int = 0,
    crossinline onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        if (unreadNotificationCount > 0)
            BadgedBox(badge = {
                Badge {
                    Text(text = unreadNotificationCount.toString())
                }
            }) {
                Icon(
                    contentDescription = stringResource(id = R.string.notifications),
                    imageVector = Icons.Filled.Notifications
                )
            }
        else
            Icon(
                contentDescription = stringResource(id = R.string.notifications),
                imageVector = Icons.Filled.Notifications
            )
    }
}