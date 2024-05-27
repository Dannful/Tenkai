package com.github.dannful.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.core.data.entity.RemoteKeyEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeyEntity: RemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeyEntity: List<RemoteKeyEntity>)

    @Query("DELETE FROM ${RemoteKeyEntity.TABLE_NAME} WHERE page = :page and status = :status")
    suspend fun deleteByPage(page: Int, status: UserMediaStatus)

    @Query("SELECT * FROM ${RemoteKeyEntity.TABLE_NAME} WHERE userMediaId = :userMediaId")
    suspend fun getById(userMediaId: Int): RemoteKeyEntity

    @Query("DELETE FROM ${RemoteKeyEntity.TABLE_NAME} WHERE page = :page and status = :status")
    suspend fun deleteAll(page: Int, status: UserMediaStatus)
}