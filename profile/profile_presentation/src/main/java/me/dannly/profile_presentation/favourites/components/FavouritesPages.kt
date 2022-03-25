package me.dannly.profile_presentation.favourites.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R

val pages
    @ReadOnlyComposable
    @Composable
    get() = arrayOf(
        stringResource(id = R.string.shows),
        stringResource(id = R.string.genres),
        stringResource(R.string.studios),
    )