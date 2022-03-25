package me.dannly.notifications_data.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.notifications_data.repository.NotificationsRepositoryImpl
import me.dannly.notifications_domain.repository.NotificationsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsDataModule {

    @Provides
    @Singleton
    fun provideNotificationsRepository(
        authenticatedApolloClient: Flow<ApolloClient?>,
        dispatcherProvider: DispatcherProvider
    ): NotificationsRepository = NotificationsRepositoryImpl(
        authenticatedApolloClient, dispatcherProvider
    )
}