package com.github.dannful.home_domain.use_case

import com.github.dannful.home_domain.repository.RemoteService

class FetchProgress(
    private val remoteService: RemoteService
) {

    suspend operator fun invoke(mediaId: Int) = remoteService.getUserMediaProgress(mediaId)
}