package me.dannly.home_presentation.user_list

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.home_domain.model.CachedScoreFormat
import me.dannly.home_domain.model.CachedUserAnime

data class UserAnimesState(
    val animeList: Map<UserAnimeStatus, Flow<PagingData<CachedUserAnime>>> = mapOf(),
    val score: String = "",
    val dialogVisible: Boolean = false,
    val cachedScoreFormat: CachedScoreFormat? = null
)
