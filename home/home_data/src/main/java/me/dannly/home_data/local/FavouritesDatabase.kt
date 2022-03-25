package me.dannly.home_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.dannly.core.domain.local.model.Favourite
import me.dannly.core.domain.local.model.Source
import me.dannly.home_data.local.entity.FavouriteEntity
import me.dannly.home_data.local.entity.SourceEntity

@Database(entities = [SourceEntity::class, FavouriteEntity::class], version = 1)
abstract class FavouritesDatabase : RoomDatabase() {

    abstract val favouritesDao: FavouritesDao
}