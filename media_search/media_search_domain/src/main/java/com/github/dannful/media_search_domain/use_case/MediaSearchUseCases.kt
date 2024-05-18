package com.github.dannful.media_search_domain.use_case

data class MediaSearchUseCases(
    val getGenres: GetGenres,
    val searchMedia: SearchMedia,
    val getScoreFormat: GetScoreFormat,
    val updateUserMedia: UpdateUserMedia,
    val getUserMedia: GetUserMedia
)