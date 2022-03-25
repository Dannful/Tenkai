package me.dannly.profile_data.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.profile_data.repository.ProfileRepositoryImpl
import me.dannly.profile_domain.repository.ProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileDataModule {

    @Provides
    @Singleton
    fun provideProfileRepository(
        authenticatedApolloClient: Flow<ApolloClient?>,
        dispatcherProvider: DispatcherProvider
    ): ProfileRepository = ProfileRepositoryImpl(
        authenticatedApolloClient,
        dispatcherProvider
    )
}