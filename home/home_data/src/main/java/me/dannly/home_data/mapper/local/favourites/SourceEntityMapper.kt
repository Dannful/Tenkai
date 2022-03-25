package me.dannly.home_data.mapper.local.favourites

import me.dannly.core.domain.local.model.Source
import me.dannly.home_data.local.entity.SourceEntity

fun SourceEntity.toSource() = Source(
    animeId, favouriteSourceName, url
)