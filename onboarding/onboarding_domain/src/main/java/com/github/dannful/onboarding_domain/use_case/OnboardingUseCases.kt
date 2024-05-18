package com.github.dannful.onboarding_domain.use_case

data class OnboardingUseCases(
    val getAccessToken: GetAccessToken,
    val getCodeFromUrl: GetCodeFromUrl,
    val saveAccessToken: SaveAccessToken,
    val getViewerId: GetViewerId,
    val saveUserId: SaveUserId
)
