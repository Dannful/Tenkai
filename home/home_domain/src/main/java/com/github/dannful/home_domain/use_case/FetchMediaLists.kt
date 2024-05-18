package com.github.dannful.home_domain.use_case

import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.home_domain.repository.PresentationService

class FetchMediaLists(
    private val presentationService: PresentationService
) {

    operator fun invoke(status: UserMediaStatus) = presentationService.fetchUserMediaLists(status)
}