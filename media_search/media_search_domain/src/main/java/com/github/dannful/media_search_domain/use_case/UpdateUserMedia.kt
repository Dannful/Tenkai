package com.github.dannful.media_search_domain.use_case

import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.repository.UserMediaService

class UpdateUserMedia(
    private val userMediaService: UserMediaService
) {

    suspend operator fun invoke(userMediaUpdate: UserMediaUpdate) =
        userMediaService.updateUserMedia(userMediaUpdate)
}