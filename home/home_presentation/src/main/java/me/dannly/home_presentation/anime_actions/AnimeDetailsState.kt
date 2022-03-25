package me.dannly.home_presentation.anime_actions

import me.dannly.core.domain.local.model.SourcedFavourite
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.home_domain.model.CachedAnime
import me.dannly.home_domain.model.CachedScoreFormat
import me.dannly.home_domain.model.CachedUserAnime

data class AnimeDetailsState(
    val cachedUserAnime: CachedUserAnime? = null,
    val cachedAnime: CachedAnime? = null,
    val favouriteWithSources: List<SourcedFavourite> = listOf(),
    val currentlySelectedSourceIndex: Int = -1,
    val buttonAdd: Boolean = true,
    val currentUrl: String? = null,
    val status: UserAnimeStatus? = null,
    val sliderProgress: Float? = null,
    val sliderText: String = "0",
    val score: String? = null,
    val updating: Boolean = false,
    val statusPopupVisible: Boolean = false,
    val cachedScoreFormat: CachedScoreFormat? = null
)
