package me.dannly.tenkai.components.action_bar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R

@Composable
inline fun SettingsButton(
    crossinline onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            contentDescription = stringResource(id = R.string.settings),
            imageVector = Icons.Filled.Settings
        )
    }
}