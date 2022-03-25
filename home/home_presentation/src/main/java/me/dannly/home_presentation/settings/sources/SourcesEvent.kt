package me.dannly.home_presentation.settings.sources

import me.dannly.core.domain.local.model.Favourite

sealed class SourcesEvent {

    data class AddSource(val favourite: Favourite): SourcesEvent()
    data class RemoveSource(val favourite: Favourite): SourcesEvent()
    data class SetNewSourceTitle(val newTitle: String): SourcesEvent()
    data class SetNewSourceUrl(val newUrl: String): SourcesEvent()
    data class SetEditDialogVisible(val visible: Boolean, val favourite: Favourite? = null): SourcesEvent()
    data class SetDeleteDialogVisible(val visible: Boolean): SourcesEvent()
}
