package com.github.dannful.media_search_domain.repository

import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.PageResult
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.media_search_domain.model.MediaSearch

interface RemoteService {

    suspend fun retrieveMedias(mediaSearch: MediaSearch, page: Int): Result<PageResult<Media>>
    suspend fun getGenreList(): Result<List<String>>
    suspend fun getUserMedia(mediaId: Int): Result<UserMedia>
}