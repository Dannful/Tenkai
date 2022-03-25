package me.dannly.home_data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.dannly.home_data.local.UserAnimeDao
import me.dannly.home_data.mapper.local.user_anime.toCachedUserAnime
import me.dannly.home_data.mapper.local.user_anime.toUserAnimeCacheEntity
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_domain.repository.LocalRepository

class LocalRepositoryImpl(
    private val userAnimeDao: UserAnimeDao
) : LocalRepository {

    override suspend fun insert(vararg cachedUserAnime: CachedUserAnime) {
        userAnimeDao.insert(*cachedUserAnime.map { it.toUserAnimeCacheEntity() }.toTypedArray())
    }

    override suspend fun deleteByStatus(status: String) = userAnimeDao.deleteByStatus(status)

    override fun getMediaLists(): Flow<List<CachedUserAnime>> {
        return userAnimeDao.getMediaLists().map { list -> list.map { it.toCachedUserAnime() } }
    }

    override suspend fun deleteMediaList(vararg cachedUserAnime: CachedUserAnime) {
        userAnimeDao.deleteMediaList(*cachedUserAnime.map { it.toUserAnimeCacheEntity() }
            .toTypedArray())
    }

    override suspend fun getById(id: Int): CachedUserAnime? {
        return userAnimeDao.getById(id)?.toCachedUserAnime()
    }

    override suspend fun deleteById(id: Int) {
        userAnimeDao.deleteById(id)
    }

    override suspend fun clear() {
        userAnimeDao.clear()
    }
}