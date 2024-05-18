package com.github.dannful.onboarding_data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo3.ApolloClient
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.onboarding_data.repository.LocalStoreService
import com.github.dannful.onboarding_data.repository.AniListRemoteService
import com.github.dannful.onboarding_domain.repository.RemoteService
import com.github.dannful.onboarding_domain.repository.StoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

@Module
@InstallIn(ViewModelComponent::class)
class OnboardingDataModule {

    @Provides
    @ViewModelScoped
    fun provideHttpClient() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    @Provides
    @ViewModelScoped
    fun provideAuthService(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider,
        apolloClientFlow: Flow<ApolloClient>
    ): RemoteService = AniListRemoteService(httpClient, dispatcherProvider, apolloClientFlow)

    @Provides
    @ViewModelScoped
    fun provideStoreService(
        dataStore: DataStore<Preferences>,
        dispatcherProvider: DispatcherProvider
    ): StoreService = LocalStoreService(dataStore, dispatcherProvider)
}