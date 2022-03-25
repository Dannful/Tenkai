package me.dannly.home_presentation.user_list.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R

val resourceMediaStatuses
    @ReadOnlyComposable
    @Composable
    get() = arrayOf(
        stringResource(R.string.finished), stringResource(R.string.releasing),
        stringResource(R.string.not_yet_released), stringResource(R.string.cancelled),
        stringResource(R.string.paused)
    )