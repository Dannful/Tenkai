package com.github.dannful.onboarding_domain.use_case

import com.github.dannful.onboarding_domain.repository.RemoteService

class GetViewerId(
    private val remoteService: RemoteService
) {

    suspend operator fun invoke() = remoteService.getViewerId()
}