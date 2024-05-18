package com.github.dannful.onboarding_domain.use_case

import com.github.dannful.onboarding_domain.repository.StoreService

class SaveUserId(
    private val storeService: StoreService
) {

    suspend operator fun invoke(userId: Int) = storeService.saveUserId(userId)
}