package me.dannly.home_data.local.entity

import androidx.room.Entity

@Entity(primaryKeys = ["animeId", "favouriteSourceName"])
data class SourceEntity(
    val animeId: Int,
    val favouriteSourceName: String,
    val url: String
)
