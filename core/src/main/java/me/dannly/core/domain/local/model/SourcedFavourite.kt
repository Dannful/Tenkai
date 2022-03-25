package me.dannly.core.domain.local.model

data class SourcedFavourite(
    val favourite: Favourite,
    val sources: List<Source>
)
