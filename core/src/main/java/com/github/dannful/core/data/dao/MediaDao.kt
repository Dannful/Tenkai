package com.github.dannful.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.dannful.core.data.entity.MediaEntity

@Dao
interface MediaDao {

    @Query("SELECT * FROM ${MediaEntity.TABLE_NAME} WHERE mediaId = :id")
    suspend fun get(id: Int): MediaEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medias: List<MediaEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mediaEntity: MediaEntity)
}