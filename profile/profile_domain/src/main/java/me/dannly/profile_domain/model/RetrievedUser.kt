package me.dannly.profile_domain.model

data class RetrievedUser(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val bannerUrl: String?,
    val description: String?,
    val createdAt: Int?,
    val meanScore: Double?,
    val episodesWatched: Int?,
    val minutesWatched: Int?,
    val favouriteGenres: List<RetrievedGenre>,
    val scores: List<RetrievedScore>,
    val studios: List<RetrievedStudio>
)
