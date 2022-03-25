package me.dannly.core.data.mapper

import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.graphql.type.MediaListStatus

fun MediaListStatus.toUserAnimeStatus() = UserAnimeStatus.valueOf(
    name
)