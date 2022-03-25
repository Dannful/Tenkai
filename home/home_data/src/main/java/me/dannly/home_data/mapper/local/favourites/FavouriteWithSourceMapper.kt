package me.dannly.home_data.mapper.local.favourites

import me.dannly.core.domain.local.model.SourcedFavourite
import me.dannly.home_data.local.entity.FavouriteWithSource

fun FavouriteWithSource.toSourcedFavourite() = SourcedFavourite(
    favouriteEntity.toFavourite(), sourceEntities.map { it.toSource() }
)