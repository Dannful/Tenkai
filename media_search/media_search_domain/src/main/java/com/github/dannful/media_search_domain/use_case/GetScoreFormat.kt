package com.github.dannful.media_search_domain.use_case

import com.github.dannful.core.domain.repository.UserMediaService

class GetScoreFormat(
    private val userMediaService: UserMediaService
) {

    suspend operator fun invoke() = userMediaService.getUserScoreFormat()
}