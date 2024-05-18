package com.github.dannful.onboarding_domain.di

import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.onboarding_domain.repository.RemoteService
import com.github.dannful.onboarding_domain.repository.StoreService
import com.github.dannful.onboarding_domain.use_case.GetAccessToken
import com.github.dannful.onboarding_domain.use_case.GetCodeFromUrl
import com.github.dannful.onboarding_domain.use_case.GetViewerId
import com.github.dannful.onboarding_domain.use_case.OnboardingUseCases
import com.github.dannful.onboarding_domain.use_case.SaveAccessToken
import com.github.dannful.onboarding_domain.use_case.SaveUserId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class OnboardingDomainModule {

    @Provides
    @ViewModelScoped
    fun provideOnboardingUseCases(
        remoteService: RemoteService,
        dispatcherProvider: DispatcherProvider,
        storeService: StoreService
    ) = OnboardingUseCases(
        getAccessToken = GetAccessToken(
            service = remoteService
        ),
        getCodeFromUrl = GetCodeFromUrl(dispatcherProvider = dispatcherProvider),
        saveAccessToken = SaveAccessToken(storeService),
        getViewerId = GetViewerId(remoteService),
        saveUserId = SaveUserId(storeService)
    )
}