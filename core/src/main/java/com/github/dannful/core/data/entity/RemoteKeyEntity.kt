package com.github.dannful.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.dannful.core.domain.model.UserMediaStatus


@Entity(tableName = RemoteKeyEntity.TABLE_NAME)
data class RemoteKeyEntity(
    @PrimaryKey val userMediaId: Int,
    val prevKey: Int?,
    val page: Int,
    val status: UserMediaStatus,
    val nextKey: Int?
) {

    companion object {
        const val TABLE_NAME = "remote_keys"
    }
}