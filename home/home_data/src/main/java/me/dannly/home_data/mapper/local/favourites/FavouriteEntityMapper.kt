package me.dannly.home_data.mapper.local.favourites

import me.dannly.core.domain.local.model.Favourite
import me.dannly.home_data.local.entity.FavouriteEntity

fun FavouriteEntity.toFavourite() = Favourite(
    name, url
)