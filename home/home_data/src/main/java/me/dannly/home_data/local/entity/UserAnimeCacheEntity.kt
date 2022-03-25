package me.dannly.home_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserAnimeCacheEntity(
    @PrimaryKey
    val id: Int,
    val animeId: Int,
    val progress: Int,
    val status: String,
    val score: Double?,
    val averageScore: Int?,
    val updatedAt: Int?,
    val animeTitle: String,
    val animeEpisodes: Int?,
    val animeAiringTime: Int?,
    val animeImage: String?,
    val animeStatus: String,
    val animeNextEpisode: Int?,
    val genres: List<String>,
    val synonyms: List<String>,
    val synopsis: String?,
    val bannerUrl: String?
)
