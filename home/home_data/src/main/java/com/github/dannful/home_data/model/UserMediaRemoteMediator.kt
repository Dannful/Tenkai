package com.github.dannful.home_data.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.dannful.core.data.entity.RemoteKeyEntity
import com.github.dannful.core.data.entity.UserMediaEntity
import com.github.dannful.core.data.mapper.toEntity
import com.github.dannful.core.data.model.UserMediaDatabase
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.home_domain.repository.RemoteService
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class UserMediaRemoteMediator(
    private val remoteService: RemoteService,
    private val userMediaStatus: UserMediaStatus,
    private val userMediaDatabase: UserMediaDatabase,
    private val dispatcherProvider: DispatcherProvider
) : RemoteMediator<Int, UserMediaEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserMediaEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    state.anchorPosition?.let { anchorPosition ->
                        state.closestItemToPosition(anchorPosition)?.let {
                            userMediaDatabase.remoteKeysDao().getById(it.userMediaId).page
                        }
                    } ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeyEntity =
                        state.pages.firstOrNull() { it.data.isNotEmpty() }?.data?.firstOrNull()
                            ?.let {
                                userMediaDatabase.remoteKeysDao().getById(it.userMediaId)
                            }
                    remoteKeyEntity?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeyEntity != null)
                }

                LoadType.APPEND -> {
                    val remoteKeyEntity =
                        state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
                            userMediaDatabase.remoteKeysDao().getById(it.userMediaId)
                        }
                    remoteKeyEntity?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeyEntity != null)
                }
            }
            val response = remoteService.getUserMediaLists(
                page = page,
                perPage = state.config.pageSize,
                status = userMediaStatus
            )
            val items = response.getOrThrow().items
            val endOfPaginationReached = items.size < state.config.pageSize
            userMediaDatabase.withTransaction {
                withContext(dispatcherProvider.IO) {
                    if (loadType == LoadType.REFRESH) {
                        userMediaDatabase.remoteKeysDao().deleteAll(page, userMediaStatus)
                        userMediaDatabase.userMediaDao().deleteByStatus(userMediaStatus)
                    }
                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val remoteKeys = items.map {
                        RemoteKeyEntity(
                            userMediaId = it.id,
                            prevKey = prevKey,
                            nextKey = nextKey,
                            page = page,
                            status = userMediaStatus
                        )
                    }
                    userMediaDatabase.remoteKeysDao().insertAll(remoteKeys)
                    userMediaDatabase.mediaDao().insertAll(items.map {
                        it.media.toEntity()
                    })
                    userMediaDatabase.userMediaDao().insertAll(items.map {
                        UserMediaEntity(
                            userMediaId = it.id,
                            status = it.status,
                            progress = it.progress,
                            startedAt = it.startedAt,
                            completedAt = it.completedAt,
                            score = it.score,
                            mediaId = it.media.id,
                            title = it.title,
                            updatedAt = it.updatedAt
                        )
                    })
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}