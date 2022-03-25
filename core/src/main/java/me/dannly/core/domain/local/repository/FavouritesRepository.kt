package me.dannly.core.domain.local.repository

import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.local.model.Favourite
import me.dannly.core.domain.local.model.SourcedFavourite
import me.dannly.core.domain.local.model.Source

interface FavouritesRepository {

    fun getFavourites(): Flow<List<SourcedFavourite>>

    suspend fun deleteSource(vararg sourceEntity: Source)

    suspend fun insertSource(sourceEntity: Source): Long

    suspend fun deleteFavourite(favouriteEntity: Favourite)

    suspend fun insertFavourite(favouriteEntity: Favourite): Long

    suspend fun getFavouriteByName(name: String): SourcedFavourite

    suspend fun getSourcesByFavouriteName(name: String): List<Source>
}