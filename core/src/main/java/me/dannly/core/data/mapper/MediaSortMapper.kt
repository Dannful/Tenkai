package me.dannly.core.data.mapper

import me.dannly.core.domain.remote.model.AnimeSort
import me.dannly.graphql.type.MediaSort

fun MediaSort.toAnimeSort() = AnimeSort
    .valueOf(name)