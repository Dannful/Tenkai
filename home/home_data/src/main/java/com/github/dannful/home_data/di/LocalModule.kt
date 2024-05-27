package com.github.dannful.home_data.di

import com.github.dannful.core.data.model.UserMediaDatabase
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.home_data.repository.RoomPagingService
import com.github.dannful.home_domain.repository.PagingService
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
    fun provideLocalService(
        userMediaDatabase: UserMediaDatabase,
        dispatcherProvider: DispatcherProvider,
        remoteService: RemoteService
    ): PagingService {
        return RoomPagingService(
            userMediaDatabase = userMediaDatabase,
            dispatcherProvider = dispatcherProvider,
            remoteService = remoteService
        )
    }
}