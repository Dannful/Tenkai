package com.github.dannful.home_presentation

import androidx.paging.PagingData
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import com.github.dannful.core_ui.components.MediaInfo
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val selectedTab: Int = 0,
    val pagingData: List<Flow<PagingData<UserMedia>>>,
    val dialogMedia: MediaInfo? = null,
    val userScoreFormat: UserScoreFormat = UserScoreFormat.BASE_10,
    val swipedMediaUpdate: UserMediaUpdate? = null
)