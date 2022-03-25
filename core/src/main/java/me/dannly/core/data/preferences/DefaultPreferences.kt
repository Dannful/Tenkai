package me.dannly.core.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.core.domain.preferences.Preferences

class DefaultPreferences(
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>,
    private val dispatcherProvider: DispatcherProvider
) : Preferences {

    override fun getAccessToken() =
        dataStore.data.flowOn(dispatcherProvider.io)
            .map { it[stringPreferencesKey(Preferences.ACCESS_TOKEN_PREFERENCE)] }
            .flowOn(dispatcherProvider.default)

    override fun getUserId() =
        dataStore.data.flowOn(dispatcherProvider.io)
            .map { it[intPreferencesKey(Preferences.USER_ID_PREFERENCE)] }
            .flowOn(dispatcherProvider.default)

    override fun getIsGrid() =
        dataStore.data.flowOn(dispatcherProvider.io)
            .map { it[booleanPreferencesKey(Preferences.IS_GRID_PREFERENCE)] }
            .flowOn(dispatcherProvider.default)

    override fun getShouldReturnToMainScreen() = dataStore.data.flowOn(dispatcherProvider.io).map {
        it[booleanPreferencesKey(Preferences.SHOULD_RETURN_TO_MAIN_SCREEN_PREFERENCE)]
    }.flowOn(dispatcherProvider.default)

    override suspend fun setAccessToken(accessToken: String) {
        withContext(dispatcherProvider.io) {
            dataStore.edit {
                it[stringPreferencesKey(Preferences.ACCESS_TOKEN_PREFERENCE)] = accessToken
            }
        }
    }

    override suspend fun setUserId(userId: Int) {
        withContext(dispatcherProvider.io) {
            dataStore.edit {
                it[intPreferencesKey(Preferences.USER_ID_PREFERENCE)] = userId
            }
        }
    }

    override suspend fun setIsGrid(isGrid: Boolean) {
        withContext(dispatcherProvider.io) {
            dataStore.edit {
                it[booleanPreferencesKey(Preferences.IS_GRID_PREFERENCE)] = isGrid
            }
        }
    }

    override suspend fun setShouldReturnToMainScreen(shouldReturnToMainScreen: Boolean) {
        withContext(dispatcherProvider.io) {
            dataStore.edit {
                it[booleanPreferencesKey(Preferences.SHOULD_RETURN_TO_MAIN_SCREEN_PREFERENCE)] =
                    shouldReturnToMainScreen
            }
        }
    }
}