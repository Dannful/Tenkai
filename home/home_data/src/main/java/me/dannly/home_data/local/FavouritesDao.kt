package me.dannly.home_data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.dannly.home_data.local.entity.FavouriteEntity
import me.dannly.home_data.local.entity.FavouriteWithSource
import me.dannly.home_data.local.entity.SourceEntity

@Dao
interface FavouritesDao {

    @Transaction
    @Query("SELECT * FROM FavouriteEntity")
    fun getFavourites(): Flow<List<FavouriteWithSource>>

    @Delete
    suspend fun deleteSource(vararg sourceEntity: SourceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(sourceEntity: SourceEntity): Long

    @Delete
    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favouriteEntity: FavouriteEntity): Long

    @Query("SELECT * FROM FavouriteEntity WHERE name = :name")
    suspend fun getFavouriteByName(name: String): FavouriteWithSource

    @Query("SELECT * FROM SourceEntity WHERE favouriteSourceName = :name")
    suspend fun getSourcesByFavouriteName(name: String): List<SourceEntity>
}