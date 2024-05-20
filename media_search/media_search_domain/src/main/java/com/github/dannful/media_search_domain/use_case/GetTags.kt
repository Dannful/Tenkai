package com.github.dannful.media_search_domain.use_case

import com.github.dannful.media_search_domain.repository.RemoteService

class GetTags(
    private val remoteService: RemoteService
) {

    suspend operator fun invoke() = remoteService.getTagList()
}