package me.dannly.home_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.dannly.home_data.local.entity.RemoteKey

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg remoteKey: RemoteKey)

    @Query("SELECT * FROM RemoteKey WHERE label = :query")
    suspend fun getByStatus(query: String): RemoteKey

    @Query("DELETE FROM RemoteKey")
    suspend fun clear()

    @Query("DELETE FROM RemoteKey WHERE label = :query")
    suspend fun deleteByQuery(query: String)
}