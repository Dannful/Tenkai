package me.dannly.core_ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R

val resourceMediaListStatuses
    @ReadOnlyComposable
    @Composable
    get() = arrayOf(
        stringResource(id = R.string.current),
        stringResource(id = R.string.planning),
        stringResource(id = R.string.completed),
        stringResource(id = R.string.dropped),
        stringResource(id = R.string.paused),
        stringResource(id = R.string.repeating)
    )