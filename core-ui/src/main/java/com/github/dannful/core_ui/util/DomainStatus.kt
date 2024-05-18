package com.github.dannful.core_ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.github.dannful.core_ui.R

@Composable
@ReadOnlyComposable
fun domainUserStatus() = listOf(
    stringResource(R.string.current),
    stringResource(R.string.planning),
    stringResource(R.string.completed),
    stringResource(R.string.dropped),
    stringResource(R.string.paused),
    stringResource(R.string.repeating)
)

@Composable
@ReadOnlyComposable
fun domainStatus() = listOf(
    stringResource(R.string.finished),
    stringResource(R.string.releasing),
    stringResource(id = R.string.not_yet_released),
    stringResource(R.string.cancelled),
    stringResource(id = R.string.paused)
)