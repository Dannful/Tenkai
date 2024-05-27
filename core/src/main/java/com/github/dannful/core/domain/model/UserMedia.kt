package com.github.dannful.core.domain.model

data class UserMedia(
    val id: Int,
    val title: String,
    val progress: Int,
    val status: UserMediaStatus,
    val score: Double,
    val media: Media,
    val startedAt: MediaDate?,
    val completedAt: MediaDate?,
    val updatedAt: Int?
)
