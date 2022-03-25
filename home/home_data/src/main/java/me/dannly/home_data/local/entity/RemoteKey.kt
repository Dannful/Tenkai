package me.dannly.home_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKey(
    @PrimaryKey
    val label: String,
    val nextKey: Int?
)
