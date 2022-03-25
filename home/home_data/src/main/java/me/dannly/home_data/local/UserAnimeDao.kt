package me.dannly.home_data.local

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.dannly.home_data.local.converters.Converters
import me.dannly.home_data.local.entity.UserAnimeCacheEntity

@Dao
interface UserAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg userAnimeCacheEntity: UserAnimeCacheEntity)

    @Query("SELECT * FROM UserAnimeCacheEntity WHERE status = :status")
    fun pagingSource(status: String): PagingSource<Int, UserAnimeCacheEntity>

    @Query("DELETE FROM UserAnimeCacheEntity WHERE status = :status")
    suspend fun deleteByStatus(status: String)

    @Query("SELECT * FROM UserAnimeCacheEntity")
    fun getMediaLists(): Flow<List<UserAnimeCacheEntity>>

    @Delete
    suspend fun deleteMediaList(vararg userAnimeCacheEntity: UserAnimeCacheEntity)

    @Query("DELETE FROM UserAnimeCacheEntity WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM UserAnimeCacheEntity")
    suspend fun clear()

    @Delete
    suspend fun deleteAll(userAnimeCacheEntities: List<UserAnimeCacheEntity>)

    @Query("SELECT * FROM UserAnimeCacheEntity WHERE animeId = :id")
    suspend fun getById(id: Int): UserAnimeCacheEntity?
}
