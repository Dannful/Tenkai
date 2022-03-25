package me.dannly.home_presentation.user_list

import me.dannly.home_domain.model.UserAnimeUpdate

sealed class UserAnimesEvent {

    data class Update(
        val userAnimeUpdate: UserAnimeUpdate,
        val onUpdate: () -> Unit
    ) : UserAnimesEvent()

    data class Delete(
        val mediaListStatus: String,
        val id: Int,
        val onDelete: () -> Unit
    ) : UserAnimesEvent()

    data class SetDialogScore(val score: String) : UserAnimesEvent()
    data class SetDialogVisible(val visible: Boolean) : UserAnimesEvent()
}
