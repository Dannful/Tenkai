package com.github.dannful.home_presentation

import com.github.dannful.core.domain.model.UserMediaUpdate

sealed class HomeEvent {

    data class SetCurrentTab(val newTab: Int) : HomeEvent()
    data class UpdateMediaList(val userMediaUpdate: UserMediaUpdate) : HomeEvent()
    data class IncreaseProgress(val mediaId: Int) : HomeEvent()
    data class DecreaseProgress(val mediaId: Int) : HomeEvent()
    data class ShowDialog(val mediaWithUpdate: MediaWithUpdate) : HomeEvent()
    data class UpdateDialogEditInfo(val userMediaUpdate: UserMediaUpdate) : HomeEvent()
    data class UpdateScore(val score: Double): HomeEvent()

    data object HideScoreAlert : HomeEvent()
    data object HideDialog : HomeEvent()
    data object RefreshUi : HomeEvent()
}