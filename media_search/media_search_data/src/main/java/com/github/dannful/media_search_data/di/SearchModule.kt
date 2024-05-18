package com.github.dannful.media_search_data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo3.ApolloClient
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.media_search_data.repository.AniListRemoteService
import com.github.dannful.media_search_data.repository.SearchPagingService
import com.github.dannful.media_search_domain.repository.PagingService
import com.github.dannful.media_search_domain.repository.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(ViewModelComponent::class)
class SearchModule {

    @Provides
    @ViewModelScoped
    fun provideRemoteService(
        dispatcherProvider: DispatcherProvider,
        apolloClientFlow: Flow<ApolloClient>,
        dataStore: DataStore<Preferences>
    ): RemoteService = AniListRemoteService(
        apolloClientFlow,
        dispatcherProvider,
        dataStore
    )

    @Provides
    @ViewModelScoped
    fun providePagingService(
        remoteService: RemoteService
    ): PagingService = SearchPagingService(
        remoteService
    )
}