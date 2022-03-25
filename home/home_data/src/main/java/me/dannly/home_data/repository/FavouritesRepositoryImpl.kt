package me.dannly.home_data.repository

import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.core.domain.local.model.Favourite
import me.dannly.core.domain.local.model.Source
import me.dannly.core.domain.local.repository.FavouritesRepository
import me.dannly.home_data.local.FavouritesDao
import me.dannly.home_data.local.FavouritesDatabase
import me.dannly.home_data.mapper.local.favourites.toFavouriteEntity
import me.dannly.home_data.mapper.local.favourites.toSource
import me.dannly.home_data.mapper.local.favourites.toSourceEntity
import me.dannly.home_data.mapper.local.favourites.toSourcedFavourite

class FavouritesRepositoryImpl(
    private val favouritesDao: FavouritesDao,
    private val dispatcherProvider: DispatcherProvider
) : FavouritesRepository {

    override fun getFavourites() =
        favouritesDao.getFavourites().map { list ->
            list.map { it.toSourcedFavourite() }
        }.flowOn(dispatcherProvider.default)

    override suspend fun deleteSource(vararg sourceEntity: Source) =
        favouritesDao.deleteSource(*withContext(dispatcherProvider.default)
        {
            sourceEntity.map { it.toSourceEntity() }.toTypedArray()
        })

    override suspend fun insertSource(sourceEntity: Source) =
        favouritesDao.insertSource(sourceEntity.toSourceEntity())

    override suspend fun deleteFavourite(favouriteEntity: Favourite) =
        favouritesDao.deleteFavourite(favouriteEntity.toFavouriteEntity())

    override suspend fun insertFavourite(favouriteEntity: Favourite) =
        favouritesDao.insertFavourite(favouriteEntity.toFavouriteEntity())

    override suspend fun getFavouriteByName(name: String) =
        favouritesDao.getFavouriteByName(name).toSourcedFavourite()

    override suspend fun getSourcesByFavouriteName(name: String) =
        withContext(dispatcherProvider.default) {
            favouritesDao
            favouritesDao.getSourcesByFavouriteName(name).map { it.toSource() }
        }
}