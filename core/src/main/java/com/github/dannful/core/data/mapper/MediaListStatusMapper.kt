package com.github.dannful.core.data.mapper

import com.github.dannful.core.domain.model.UserMediaStatus

fun UserMediaStatus.toDataMediaListStatus() =
    com.github.dannful.models.type.MediaListStatus.valueOf(
        name
    )

fun com.github.dannful.models.type.MediaListStatus.toDomainMediaListStatus() =
    UserMediaStatus.valueOf(
        name
    )