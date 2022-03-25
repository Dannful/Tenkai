package me.dannly.home_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.dannly.home_data.local.converters.Converters
import me.dannly.home_data.local.entity.RemoteKey
import me.dannly.home_data.local.entity.UserAnimeCacheEntity

@Database(entities = [UserAnimeCacheEntity::class, RemoteKey::class], version = 5)
@TypeConverters(Converters::class)
abstract class UserAnimeDatabase : RoomDatabase() {

    abstract val userAnimeDao: UserAnimeDao
    abstract val remoteKeysDao: RemoteKeysDao
}