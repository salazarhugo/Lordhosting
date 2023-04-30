package com.salazar.lordhosting.core.data.datastore

import androidx.datastore.core.DataStore
import com.salazar.lordhosting.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject


class DataStoreRepositoryImpl @Inject constructor(
    private val settingsStore: DataStore<Settings>,
) {
    val userPreferencesFlow: Flow<Settings> = settingsStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(Settings.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun resetSettings() {
        settingsStore.updateData {
            it.toBuilder().clear().build()
        }
    }

    suspend fun getApiKey(): String {
        return userPreferencesFlow.first().apiKey
    }

    suspend fun getXSRFToken(): String {
        return userPreferencesFlow.first().xsrfToken
    }

    suspend fun getCookie(): List<String> {
        return userPreferencesFlow.first().cookiesList
    }

    suspend fun updateApiKey(apiKey: String) {
        settingsStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setApiKey(apiKey).build()
        }
    }

    suspend fun updateCookie(cookies: List<String>) {
        settingsStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().addAllCookies(cookies).build()
        }
    }

    suspend fun updateXSRFToken(token: String) {
        settingsStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setXsrfToken(token).build()
        }
    }
}