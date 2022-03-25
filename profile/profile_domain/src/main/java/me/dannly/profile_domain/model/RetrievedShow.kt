package me.dannly.profile_domain.model

data class RetrievedShow(
    val id: Int,
    val averageScore: Int?,
    val userPreferredTitle: String,
    val imageUrl: String?
)
