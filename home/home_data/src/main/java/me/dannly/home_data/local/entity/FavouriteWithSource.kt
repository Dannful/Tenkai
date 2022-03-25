package me.dannly.home_data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class FavouriteWithSource(
    @Embedded val favouriteEntity: FavouriteEntity,
    @Relation(
        parentColumn = "name",
        entityColumn = "favouriteSourceName"
    )
    val sourceEntities: List<SourceEntity>
)
