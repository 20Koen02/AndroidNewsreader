package nl.vanwijngaarden.koen.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ApplicationPreferences(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("ApplicationPreferences")

        val MATERIAL_YOU_KEY = booleanPreferencesKey("material_you")
        val THEME_KEY = intPreferencesKey("theme")
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    val getMaterialYou: Flow<Boolean> =
        context.dataStore.data.map { it[MATERIAL_YOU_KEY] ?: false }

    suspend fun saveMaterialYou(state: Boolean) {
        context.dataStore.edit { it[MATERIAL_YOU_KEY] = state }
    }

    val getTheme: Flow<Int> =
        context.dataStore.data.map { it[THEME_KEY] ?: 0 }

    suspend fun saveTheme(state: Int) {
        context.dataStore.edit { it[THEME_KEY] = state }
    }

    val getToken: Flow<String?> =
        context.dataStore.data.map { it[TOKEN_KEY] ?: "" }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }
}