package me.dannly.home_presentation.settings.sources

import me.dannly.core.domain.local.model.Favourite

data class SourcesState(
    val newSourceTitle: String = "",
    val newSourceUrl: String = "",
    val editDialogVisible: Boolean = false,
    val deleteDialogVisible: Boolean = false,
    val currentlySelectedFavourite: Favourite? = null
)
