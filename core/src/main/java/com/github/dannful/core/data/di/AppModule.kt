package com.github.dannful.core.data.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.github.dannful.core.data.model.DefaultDispatcherProvider
import com.github.dannful.core.data.model.UserMediaDatabase
import com.github.dannful.core.data.repository.AniListService
import com.github.dannful.core.data.repository.UserMediaRoomService
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.domain.repository.RoomService
import com.github.dannful.core.domain.repository.UserMediaService
import com.github.dannful.core.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.runningReduce
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDataStore(application: Application) =
        preferencesDataStore(name = Constants.DATA_STORE_NAME)
            .getValue(application, DataStore<Preferences>::data)

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideApolloClient(
        dataStore: DataStore<Preferences>
    ) = dataStore.data.mapNotNull {
        it[stringPreferencesKey(Constants.DATA_STORE_TOKEN_KEY_NAME)]
    }.map {
        ApolloClient.Builder()
            .serverUrl(Constants.BASE_URL)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor(it)).build()
            )
            .build()
    }.runningReduce { previousClient, newClient ->
        previousClient.close()
        newClient
    }

    @Provides
    @Singleton
    fun provideUserMediaService(
        dispatcherProvider: DispatcherProvider,
        apolloClientFlow: Flow<ApolloClient>,
        application: Application
    ): UserMediaService = AniListService(
        dispatcherProvider = dispatcherProvider,
        apolloClientFlow = apolloClientFlow,
        application = application
    )

    private class AuthorizationInterceptor(
        private val token: String
    ) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .apply {
                    addHeader("Authorization", token)
                }
                .build()
            return chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideUserMediaDatabase(
        application: Application
    ) = Room.databaseBuilder(
        application,
        UserMediaDatabase::class.java,
        UserMediaDatabase.USER_MEDIA_DATABASE
    ).build()

    @Provides
    @Singleton
    fun provideRoomService(
        dispatcherProvider: DispatcherProvider,
        userMediaDatabase: UserMediaDatabase
    ): RoomService = UserMediaRoomService(
        dispatcherProvider,
        userMediaDatabase
    )
}