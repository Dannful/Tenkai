package me.dannly.tenkai.components.toolbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R

inline fun getDefaultToolbarIcon(crossinline onClick: () -> Unit) = @Composable {
    IconButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(
            contentDescription = stringResource(id = R.string.menu),
            imageVector = Icons.Filled.Menu
        )
    }
}