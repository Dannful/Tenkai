package me.dannly.browse_data.mapper

import me.dannly.browse_domain.model.SearchedMedia
import me.dannly.browse_domain.model.SearchedMediaStatus
import me.dannly.graphql.fragment.AnimeFragment

fun AnimeFragment.toSearchedMedia() = SearchedMedia(
    id = id,
    userPreferredTitle = title!!.userPreferred!!,
    averageScore = averageScore,
    searchedMediaStatus = SearchedMediaStatus.valueOf(status!!.name),
    imageUrl = coverImage?.medium
)