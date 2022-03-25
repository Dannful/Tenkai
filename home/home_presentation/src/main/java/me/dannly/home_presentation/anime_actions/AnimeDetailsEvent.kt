package me.dannly.home_presentation.anime_actions

import me.dannly.core.domain.local.model.Source
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_domain.model.UserAnimeUpdate

sealed class AnimeDetailsEvent {

    sealed class AnimeEditEvent {
        data class SetSliderProgress(val newProgress: Float) : AnimeEditEvent()
        data class SetSliderText(val newText: String) : AnimeEditEvent()
        data class SetStatusPopupVisible(val visible: Boolean) : AnimeEditEvent()
        data class SetStatus(val newStatus: UserAnimeStatus?) : AnimeEditEvent()
        data class SetScore(val newScore: String?) : AnimeEditEvent()
        data class UpdateUserAnime(
            val userAnimeUpdate: UserAnimeUpdate,
            val updateFinished: (CachedUserAnime) -> Unit
        ) : AnimeEditEvent()

        data class DeleteUserAnime(val id: Int) : AnimeEditEvent()
    }

    sealed class AnimeWatchEvent {
        data class SetSourceIndex(val index: Int) : AnimeWatchEvent()
        data class AddSource(val source: Source) : AnimeWatchEvent()
        data class RemoveSource(val source: Source) : AnimeWatchEvent()
        data class SetAdd(val add: Boolean) : AnimeWatchEvent()
        data class SetUrl(val url: String) : AnimeWatchEvent()
    }
}
