package me.dannly.tenkaiapp.di

import android.app.Application
import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.dannly.core.data.coroutines.DispatcherProviderImpl
import me.dannly.core.data.preferences.DefaultPreferences
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.core.domain.preferences.Preferences
import me.dannly.core.domain.preferences.settingsDataStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()

    @Provides
    @Singleton
    fun providePreferences(app: Application, dispatcherProvider: DispatcherProvider): Preferences =
        DefaultPreferences(app.settingsDataStore, dispatcherProvider)

    @Provides
    @Singleton
    fun provideUserId(
        preferences: Preferences
    ): Flow<Int?> = preferences.getUserId()

    @Provides
    @Singleton
    fun provideAuthenticatedApolloClient(preferences: Preferences) =
        preferences.getAccessToken().map { accessToken ->
            accessToken?.let {
                ApolloClient.builder().serverUrl("https://graphql.anilist.co")
                    .okHttpClient(
                        OkHttpClient.Builder()
                            .addInterceptor(
                                AuthorizationInterceptor(
                                    it
                                )
                            )
                            .build()
                    )
                    .build()
            }
        }

    private class AuthorizationInterceptor(private val accessToken: String) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken").build()
            )
        }
    }
}