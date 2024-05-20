package com.github.dannful.media_search_presentation

import androidx.paging.PagingData
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import com.github.dannful.core_ui.components.MediaInfo
import com.github.dannful.media_search_domain.model.MediaSearch
import com.github.dannful.media_search_domain.model.MediaSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MediaSearchState(
    val mediaSearch: MediaSearch = MediaSearch(
        query = "",
        seasonYear = null,
        genres = emptySet(),
        tags = emptySet(),
        sort = listOf(MediaSort.POPULARITY_DESC),
        type = null
    ),
    val pagingData: Flow<PagingData<Media>> = emptyFlow(),
    val genres: List<String> = emptyList(),
    val allTags: List<String> = emptyList(),
    val filteredTags: List<String> = emptyList(),
    val tagSearch: String = "",
    val tagSearchActive: Boolean = false,
    val startDateDialogVisible: Boolean = false,
    val years: List<Int> = emptyList(),
    val clickedMedia: MediaInfo? = null,
    val scoreFormat: UserScoreFormat = UserScoreFormat.BASE_10,
)
