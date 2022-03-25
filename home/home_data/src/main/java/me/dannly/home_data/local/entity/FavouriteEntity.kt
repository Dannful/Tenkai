package me.dannly.home_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(
    @PrimaryKey
    val name: String,
    val url: String
)
