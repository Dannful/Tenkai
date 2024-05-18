package com.github.dannful.onboarding_domain.use_case

import com.github.dannful.onboarding_domain.repository.RemoteService

class GetAccessToken(
    private val service: RemoteService
) {

    suspend operator fun invoke(code: String) = service.getAccessToken(code)
}