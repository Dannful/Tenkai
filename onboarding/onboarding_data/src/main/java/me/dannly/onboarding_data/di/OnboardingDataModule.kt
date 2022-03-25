package me.dannly.onboarding_data.di

import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.onboarding_data.repository.LoginRepositoryImpl
import me.dannly.onboarding_domain.repository.LoginRepository

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDataModule {

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(
        authenticatedApolloClient: Flow<ApolloClient?>,
        dispatcherProvider: DispatcherProvider
    ): LoginRepository = LoginRepositoryImpl(
        authenticatedApolloClient,
        dispatcherProvider
    )
}