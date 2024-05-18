package com.github.dannful.home_data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo3.ApolloClient
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.home_data.repository.GraphQlRemoteService
import com.github.dannful.home_domain.repository.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(ViewModelComponent::class)
class RemoteModule {

    @Provides
    @ViewModelScoped
    fun provideRemoteService(
        apolloClientFlow: Flow<ApolloClient>,
        dataStore: DataStore<Preferences>,
        dispatcherProvider: DispatcherProvider
    ): RemoteService = GraphQlRemoteService(
        apolloClientFlow,
        dataStore,
        dispatcherProvider
    )
}