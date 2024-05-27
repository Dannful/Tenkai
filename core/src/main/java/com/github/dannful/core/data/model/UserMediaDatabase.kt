package com.github.dannful.core.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.dannful.core.data.dao.MediaDao
import com.github.dannful.core.data.dao.RemoteKeysDao
import com.github.dannful.core.data.dao.UserMediaDao
import com.github.dannful.core.data.entity.MediaEntity
import com.github.dannful.core.data.entity.RemoteKeyEntity
import com.github.dannful.core.data.entity.UserMediaEntity

@Database(
    entities = [UserMediaEntity::class, MediaEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserMediaDatabase : RoomDatabase() {

    companion object {

        const val USER_MEDIA_DATABASE = "media.db"
    }

    abstract fun userMediaDao(): UserMediaDao
    abstract fun mediaDao(): MediaDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}