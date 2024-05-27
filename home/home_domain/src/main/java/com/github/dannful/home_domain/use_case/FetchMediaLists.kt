package com.github.dannful.home_domain.use_case

import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.home_domain.repository.PagingService

class FetchMediaLists(
    private val pagingService: PagingService
) {

    operator fun invoke(status: UserMediaStatus) = pagingService.pagingService(status)
}