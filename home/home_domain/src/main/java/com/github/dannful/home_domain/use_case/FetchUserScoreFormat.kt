package com.github.dannful.home_domain.use_case

import com.github.dannful.core.domain.repository.UserMediaService

class FetchUserScoreFormat(
    private val userMediaService: UserMediaService
) {

    suspend operator fun invoke() = userMediaService.getUserScoreFormat()
}