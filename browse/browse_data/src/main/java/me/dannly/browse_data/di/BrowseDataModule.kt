package me.dannly.browse_data.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import me.dannly.browse_data.repository.BrowseRepositoryImpl
import me.dannly.browse_domain.repository.BrowseRepository
import me.dannly.core.domain.coroutines.DispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BrowseDataModule {

    @Provides
    @Singleton
    fun provideBrowseRepository(
        authenticatedApolloClient: Flow<ApolloClient?>,
        dispatcherProvider: DispatcherProvider
    ): BrowseRepository = BrowseRepositoryImpl(
        authenticatedApolloClient, dispatcherProvider
    )
}