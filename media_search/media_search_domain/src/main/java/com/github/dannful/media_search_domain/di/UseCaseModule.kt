package com.github.dannful.media_search_domain.di

import com.github.dannful.core.domain.repository.UserMediaService
import com.github.dannful.media_search_domain.repository.PagingService
import com.github.dannful.media_search_domain.repository.RemoteService
import com.github.dannful.media_search_domain.use_case.GetGenres
import com.github.dannful.media_search_domain.use_case.GetScoreFormat
import com.github.dannful.media_search_domain.use_case.GetUserMedia
import com.github.dannful.media_search_domain.use_case.MediaSearchUseCases
import com.github.dannful.media_search_domain.use_case.SearchMedia
import com.github.dannful.media_search_domain.use_case.UpdateUserMedia
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideUseCases(
        remoteService: RemoteService,
        pagingService: PagingService,
        userMediaService: UserMediaService
    ) = MediaSearchUseCases(
        getGenres = GetGenres(remoteService),
        searchMedia = SearchMedia(pagingService),
        getScoreFormat = GetScoreFormat(userMediaService),
        updateUserMedia = UpdateUserMedia(userMediaService),
        getUserMedia = GetUserMedia(remoteService)
    )
}