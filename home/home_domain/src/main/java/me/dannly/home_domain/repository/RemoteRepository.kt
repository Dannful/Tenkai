package me.dannly.home_domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.graphql.type.ScoreFormat
import me.dannly.home_domain.model.CachedAnime
import me.dannly.home_domain.model.CachedScoreFormat
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_domain.model.UserAnimeUpdate

interface RemoteRepository {

    suspend fun updateAnime(
        userAnimeUpdate: UserAnimeUpdate
    ): Result<CachedUserAnime>

    suspend fun deleteAnime(id: Int): Result<Boolean>

    suspend fun getUserAnimes(page: Int, userAnimeStatus: UserAnimeStatus, vararg mediaListSort: String): Result<List<CachedUserAnime>>

    suspend fun getUserAnimeByMediaId(mediaId: Int): Result<CachedUserAnime>

    suspend fun getAnimeById(animeId: Int): Result<CachedAnime>

    suspend fun getUserScoreFormat(): Result<CachedScoreFormat>

    fun getPagedAnimeList(userAnimeStatus: UserAnimeStatus): Flow<PagingData<CachedUserAnime>>
}