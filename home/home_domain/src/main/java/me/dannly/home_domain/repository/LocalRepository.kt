package me.dannly.home_domain.repository

import kotlinx.coroutines.flow.Flow
import me.dannly.home_domain.model.CachedUserAnime

interface LocalRepository {

    suspend fun insert(vararg cachedUserAnime: CachedUserAnime)

    suspend fun deleteByStatus(status: String)

    fun getMediaLists(): Flow<List<CachedUserAnime>>

    suspend fun deleteMediaList(vararg cachedUserAnime: CachedUserAnime)

    suspend fun getById(id: Int): CachedUserAnime?

    suspend fun deleteById(id: Int)

    suspend fun clear()
}