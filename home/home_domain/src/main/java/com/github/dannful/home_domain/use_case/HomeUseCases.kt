package com.github.dannful.home_domain.use_case

data class HomeUseCases(
    val fetchMediaLists: FetchMediaLists,
    val updateMediaList: UpdateMediaList,
    val fetchUserScoreFormat: FetchUserScoreFormat,
    val fetchProgress: FetchProgress
)
