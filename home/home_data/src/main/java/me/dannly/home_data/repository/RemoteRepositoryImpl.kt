package me.dannly.home_data.repository

import androidx.paging.*
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.graphql.*
import me.dannly.graphql.type.MediaListSort
import me.dannly.graphql.type.MediaListStatus
import me.dannly.home_data.local.UserAnimeDatabase
import me.dannly.home_data.mapper.local.user_anime.toCachedUserAnime
import me.dannly.home_data.mapper.remote.toCachedAnime
import me.dannly.home_data.mapper.remote.toCachedScoreFormat
import me.dannly.home_data.mapper.remote.toCachedUserAnime
import me.dannly.home_data.paging.UserAnimesRemoteMediator
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_domain.model.UserAnimeUpdate
import me.dannly.home_domain.repository.RemoteRepository

@OptIn(
    ExperimentalCoroutinesApi::class,
    ExperimentalPagingApi::class
)
class RemoteRepositoryImpl(
    private val userId: Flow<Int?>,
    private val authenticatedApolloClient: Flow<ApolloClient?>,
    private val dispatcherProvider: DispatcherProvider,
    private val userAnimeDatabase: UserAnimeDatabase
) : RemoteRepository {

    override suspend fun updateAnime(userAnimeUpdate: UserAnimeUpdate) =
        withContext(dispatcherProvider.io) {
            try {
                Result.success(
                    authenticatedApolloClient.first()!!.mutate(
                        UserAnimeMutation(
                            mediaId = userAnimeUpdate.mediaId.toInput(),
                            mediaStatus = MediaListStatus.valueOf(userAnimeUpdate.mediaListStatus)
                                .toInput(),
                            progress = userAnimeUpdate.progress.toInput(),
                            score = userAnimeUpdate.score.toInput()
                        )
                    )
                        .await().data!!.saveMediaListEntry!!.fragments.userAnimeFragment.toCachedUserAnime()
                )
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }

    override suspend fun deleteAnime(id: Int) = withContext(dispatcherProvider.io) {
        try {
            Result.success(
                authenticatedApolloClient.first()!!.mutate(
                    DeleteUserAnimeMutation(id.toInput())
                ).await().data!!.deleteMediaListEntry!!.deleted!!
            )
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override fun getPagedAnimeList(userAnimeStatus: UserAnimeStatus): Flow<PagingData<CachedUserAnime>> {
        return Pager(
            config = PagingConfig(
                pageSize = UserAnimesRemoteMediator.MEDIA_LISTS_PER_PAGE
            ),
            remoteMediator = UserAnimesRemoteMediator(
                userAnimeStatus, userAnimeDatabase, this
            )
        ) {
            userAnimeDatabase.userAnimeDao.pagingSource(userAnimeStatus.name)
        }.flow.flowOn(dispatcherProvider.io).map { pagingData ->
            pagingData.map {
                it.toCachedUserAnime()
            }
        }.flowOn(dispatcherProvider.default)
    }

    override suspend fun getAnimeById(animeId: Int) = try {
        Result.success(
            authenticatedApolloClient.first()!!.query(
                AnimeSearchByIdQuery(animeId = animeId.toInput())
            ).await().data!!.media!!.fragments.animeFragment.toCachedAnime()
        )
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getUserScoreFormat() = withContext(dispatcherProvider.io) {
        try {
            Result.success(
                authenticatedApolloClient.first()!!.query(
                    UserScoreFormatQuery()
                ).await().data!!.viewer!!.mediaListOptions!!.scoreFormat!!.toCachedScoreFormat()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserAnimes(
        page: Int,
        userAnimeStatus: UserAnimeStatus,
        vararg mediaListSort: String
    ) = try {
        Result.success(authenticatedApolloClient.first()!!.query(
            UserAnimeListQuery(
                userId = userId.first().toInput(),
                status = MediaListStatus.valueOf(userAnimeStatus.name).toInput(),
                page = page.toInput(),
                perPage = UserAnimesRemoteMediator.MEDIA_LISTS_PER_PAGE.toInput(),
                sort = mediaListSort.map { MediaListSort.valueOf(it) }.toList().toInput()
            )
        )
            .await().data!!.page!!.mediaList!!.mapNotNull { it?.fragments?.userAnimeFragment?.toCachedUserAnime() })
    } catch (exception: Exception) {
        Result.failure(exception)
    }

    override suspend fun getUserAnimeByMediaId(mediaId: Int) = try {
        Result.success(
            authenticatedApolloClient.first()!!.query(
                UserAnimeQuery(
                    userId = userId.first().toInput(),
                    mediaId = mediaId.toInput()
                )
            )
                .await().data!!.mediaList!!.fragments.userAnimeFragment.toCachedUserAnime()
        )
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}