package com.github.dannful.onboarding_data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.util.Constants
import com.github.dannful.onboarding_domain.repository.StoreService
import kotlinx.coroutines.withContext

class LocalStoreService(
    private val dataStore: DataStore<Preferences>,
    private val dispatcherProvider: DispatcherProvider
) : StoreService {

    override suspend fun saveAccessToken(token: String) {
        withContext(dispatcherProvider.IO) {
            dataStore.edit {
                it[stringPreferencesKey(Constants.DATA_STORE_TOKEN_KEY_NAME)] = token
            }
        }
    }

    override suspend fun saveUserId(userId: Int) {
        withContext(dispatcherProvider.IO) {
            dataStore.edit {
                it[intPreferencesKey(Constants.DATA_STORE_USER_ID_KEY_NAME)] = userId
            }
        }
    }
}