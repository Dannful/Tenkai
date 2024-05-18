package com.github.dannful.onboarding_domain.use_case

import com.github.dannful.onboarding_domain.repository.StoreService

class SaveAccessToken(
    private val storeService: StoreService
) {

    suspend operator fun invoke(accessToken: String) {
        storeService.saveAccessToken(accessToken)
    }
}