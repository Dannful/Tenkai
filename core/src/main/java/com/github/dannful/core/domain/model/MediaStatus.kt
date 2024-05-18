package com.github.dannful.core.domain.model

enum class MediaStatus {

    FINISHED, RELEASING, NOT_YET_RELEASED, CANCELLED, PAUSED, UNKNOWN;

    val isWatchable
        get() = this in listOf(RELEASING, FINISHED, PAUSED)
}