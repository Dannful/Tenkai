package com.github.dannful.home_domain.di

import com.github.dannful.core.domain.repository.UserMediaService
import com.github.dannful.home_domain.repository.PagingService
import com.github.dannful.home_domain.repository.RemoteService
import com.github.dannful.home_domain.use_case.FetchMediaLists
import com.github.dannful.home_domain.use_case.FetchProgress
import com.github.dannful.home_domain.use_case.FetchUserScoreFormat
import com.github.dannful.home_domain.use_case.HomeUseCases
import com.github.dannful.home_domain.use_case.UpdateMediaList
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
        pagingService: PagingService,
        userMediaService: UserMediaService,
        remoteService: RemoteService
    ) = HomeUseCases(
        fetchMediaLists = FetchMediaLists(pagingService),
        updateMediaList = UpdateMediaList(userMediaService),
        fetchUserScoreFormat = FetchUserScoreFormat(userMediaService),
        fetchProgress = FetchProgress(remoteService)
    )
}