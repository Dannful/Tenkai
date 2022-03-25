package me.dannly.core.domain.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import me.dannly.core.domain.preferences.Preferences.Companion.SETTINGS_DATA_STORE

interface Preferences {

    fun getAccessToken(): Flow<String?>
    fun getUserId(): Flow<Int?>

    fun getIsGrid(): Flow<Boolean?>
    fun getShouldReturnToMainScreen(): Flow<Boolean?>

    suspend fun setAccessToken(accessToken: String)
    suspend fun setUserId(userId: Int)

    suspend fun setIsGrid(isGrid: Boolean)
    suspend fun setShouldReturnToMainScreen(shouldReturnToMainScreen: Boolean)

    companion object {

        const val SETTINGS_DATA_STORE = "settings"

        const val ACCESS_TOKEN_PREFERENCE = "access_token"
        const val USER_ID_PREFERENCE = "user_id"
        const val IS_GRID_PREFERENCE = "is_grid"
        const val SHOULD_RETURN_TO_MAIN_SCREEN_PREFERENCE = "should_return_to_main_screen"
    }
}

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_DATA_STORE)