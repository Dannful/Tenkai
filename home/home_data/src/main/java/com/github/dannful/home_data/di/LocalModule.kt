package com.github.dannful.home_data.di

import com.github.dannful.home_data.repository.PagingService
import com.github.dannful.home_domain.repository.PresentationService
import com.github.dannful.home_domain.repository.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class LocalModule {

    @Provides
    @ViewModelScoped
    fun providePresentationService(
        remoteService: RemoteService
    ): PresentationService = PagingService(
        remoteService
    )
}