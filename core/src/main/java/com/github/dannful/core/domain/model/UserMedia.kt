package com.github.dannful.core.domain.model

data class UserMedia(
    val id: Int,
    val title: String,
    val progress: Int,
    val status: UserMediaStatus,
    val media: Media,
    val score: Double,
    val startedAt: MediaDate?,
    val completedAt: MediaDate?
)
