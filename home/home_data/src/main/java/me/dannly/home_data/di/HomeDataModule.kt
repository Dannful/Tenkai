package me.dannly.home_data.di

import android.app.Application
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.core.domain.local.repository.FavouritesRepository
import me.dannly.home_data.local.FavouritesDatabase
import me.dannly.home_data.local.UserAnimeDatabase
import me.dannly.home_data.repository.FavouritesRepositoryImpl
import me.dannly.home_data.repository.LocalRepositoryImpl
import me.dannly.home_data.repository.RemoteRepositoryImpl
import me.dannly.home_domain.repository.LocalRepository
import me.dannly.home_domain.repository.RemoteRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeDataModule {

    @Provides
    @Singleton
    fun provideUserAnimeDatabase(app: Application): UserAnimeDatabase = Room.databaseBuilder(
        app,
        UserAnimeDatabase::class.java,
        "user_anime_list"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideRemoteRepository(
        userId: Flow<Int?>,
        authenticatedApolloClient: Flow<ApolloClient?>,
        dispatcherProvider: DispatcherProvider,
        userAnimeDatabase: UserAnimeDatabase
    ): RemoteRepository = RemoteRepositoryImpl(
        userId,
        authenticatedApolloClient,
        dispatcherProvider,
        userAnimeDatabase
    )

    @Provides
    @Singleton
    fun provideLocalRepository(
        userAnimeDatabase: UserAnimeDatabase
    ): LocalRepository = LocalRepositoryImpl(
        userAnimeDatabase.userAnimeDao
    )

    @Provides
    @Singleton
    fun provideFavouritesDatabase(
        application: Application
    ) = Room.databaseBuilder(
        application,
        FavouritesDatabase::class.java,
        "favourites"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideFavouritesRepository(
        favouritesDatabase: FavouritesDatabase,
        dispatcherProvider: DispatcherProvider
    ): FavouritesRepository = FavouritesRepositoryImpl(
        favouritesDatabase.favouritesDao, dispatcherProvider
    )
}