package me.dannly.core.util

sealed class UiEvent {

    data class ShowToast(val uiText: UiText): UiEvent()
    object CloseApp: UiEvent()
    object NavigateBack: UiEvent()
}
