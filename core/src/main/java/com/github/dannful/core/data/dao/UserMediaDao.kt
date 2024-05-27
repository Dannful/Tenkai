package com.github.dannful.core.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.github.dannful.core.data.entity.MediaEntity
import com.github.dannful.core.data.entity.UserMediaEntity
import com.github.dannful.core.domain.model.UserMediaStatus

@Dao
interface UserMediaDao {

    @Query(
        "SELECT * FROM ${UserMediaEntity.TABLE_NAME} JOIN ${MediaEntity.TABLE_NAME} "
                + "ON ${UserMediaEntity.TABLE_NAME}.mediaId = ${MediaEntity.TABLE_NAME}.mediaId WHERE ${UserMediaEntity.TABLE_NAME}.status = :status "
                + "ORDER BY ${UserMediaEntity.TABLE_NAME}.updatedAt DESC"
    )
    fun pagingSource(status: UserMediaStatus): PagingSource<Int, UserMediaEntity>

    @Query("SELECT * FROM ${UserMediaEntity.TABLE_NAME} WHERE mediaId = :id")
    suspend fun getByMediaId(id: Int): UserMediaEntity

    @Update
    suspend fun update(userMediaEntity: UserMediaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userMediaEntities: List<UserMediaEntity>)

    @Query("DELETE FROM ${UserMediaEntity.TABLE_NAME} WHERE status = :status")
    suspend fun deleteByStatus(status: UserMediaStatus)
}