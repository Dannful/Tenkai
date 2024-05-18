package com.github.dannful.home_domain.use_case

import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.repository.UserMediaService

class UpdateMediaList(
    private val userMediaService: UserMediaService
) {

    suspend operator fun invoke(userMediaUpdate: UserMediaUpdate) =
        userMediaService.updateUserMedia(userMediaUpdate)
}