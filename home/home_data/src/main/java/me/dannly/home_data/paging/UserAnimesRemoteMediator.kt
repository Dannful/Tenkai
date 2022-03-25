package me.dannly.home_data.paging

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.home_data.local.UserAnimeDatabase
import me.dannly.home_data.local.entity.RemoteKey
import me.dannly.home_data.local.entity.UserAnimeCacheEntity
import me.dannly.home_data.mapper.local.user_anime.toUserAnimeCacheEntity
import me.dannly.home_domain.repository.RemoteRepository

@OptIn(ExperimentalPagingApi::class)
class UserAnimesRemoteMediator(
    private val userAnimeStatus: UserAnimeStatus,
    private val userAnimeDatabase: UserAnimeDatabase,
    private val remoteRepository: RemoteRepository
) : RemoteMediator<Int, UserAnimeCacheEntity>() {

    companion object {

        const val MEDIA_LISTS_PER_PAGE = 20
    }

    private val remoteKeysDao = userAnimeDatabase.remoteKeysDao
    private val userAnimeDao = userAnimeDatabase.userAnimeDao

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserAnimeCacheEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    userAnimeDatabase.withTransaction {
                        remoteKeysDao.getByStatus(
                            userAnimeStatus.name
                        )
                    }.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
            }
            val response = remoteRepository.getUserAnimes(loadKey, userAnimeStatus).getOrThrow()
            val completed = response.size < state.config.pageSize

            userAnimeDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.deleteByQuery(userAnimeStatus.name)
                    userAnimeDao.deleteByStatus(userAnimeStatus.name)
                }
                remoteKeysDao.insert(
                    RemoteKey(
                        label = userAnimeStatus.name,
                        nextKey = loadKey.plus(1).takeUnless { completed }
                    )
                )
                userAnimeDao.insert(*response.map { it.toUserAnimeCacheEntity() }
                    .toTypedArray())
            }
            MediatorResult.Success(endOfPaginationReached = completed)
        } catch (exception: Exception) {
            MediatorResult.Error(exception)
        }
    }
}