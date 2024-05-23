package com.github.dannful.core.domain.model

import com.github.dannful.core.data.model.QueryInput
import kotlinx.serialization.Serializable

@Serializable
data class UserMediaUpdate(
    val mediaId: QueryInput<Int> = QueryInput.absent(),
    val score: QueryInput<Double> = QueryInput.absent(),
    val progress: QueryInput<Int> = QueryInput.absent(),
    val startedAt: QueryInput<MediaDate> = QueryInput.absent(),
    val completedAt: QueryInput<MediaDate> = QueryInput.absent(),
    val status: QueryInput<UserMediaStatus> = QueryInput.absent()
)
