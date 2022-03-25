package me.dannly.browse_domain.model

data class SearchedMedia(
    val id: Int,
    val userPreferredTitle: String,
    val averageScore: Int?,
    val searchedMediaStatus: SearchedMediaStatus,
    val imageUrl: String?
)
