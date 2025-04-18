package com.example.pupilmeshprojects

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.datastore.preferences.remove
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore = context.createDataStore(name = "session")

    companion object {
        val KEY_EMAIL = preferencesKey<String>("email")
    }

    suspend fun saveUser(email: String) {
        dataStore.edit { it[KEY_EMAIL] = email }
    }

    val signedInUser: Flow<String?> = dataStore.data.map {
        it[KEY_EMAIL]
    }

    suspend fun clearSession() {
        dataStore.edit { it.remove(KEY_EMAIL) }
    }
}