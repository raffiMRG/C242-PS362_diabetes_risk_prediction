package com.capstone.diabticapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
val Context.persistentDataStore: DataStore<Preferences> by preferencesDataStore(name = "persistent_data")

class UserPreference private constructor(
    private val dataStore: DataStore<Preferences>,
    private val persistentDataStore: DataStore<Preferences>
) {

    suspend fun saveDiabetesStatus(isDiabetes: Boolean, username: String) {
        persistentDataStore.edit { preferences ->
            preferences[booleanPreferencesKey("is_diabetes_$username")] = isDiabetes
        }
    }

    fun getDiabetesStatus(username: String): Flow<Boolean> {
        return persistentDataStore.data.map { preferences ->
            preferences[booleanPreferencesKey("is_diabetes_$username")] ?: true
        }
    }

    suspend fun updateAccessToken(newAccessToken: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = newAccessToken
        }
    }

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[REFRESH_TOKEN_KEY] = user.refreshToken
            preferences[IS_LOGIN_KEY] = user.isLogin
            preferences[NAME_KEY] = user.username
            preferences[PASSWORD_KEY] = user.password
            user.phone?.let { preferences[PHONE_KEY] = it }
            user.photoUrl?.let { preferences[PHOTO_URL_KEY] = it }
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                email = preferences[EMAIL_KEY] ?: "",
                token = preferences[TOKEN_KEY] ?: "",
                refreshToken = preferences[REFRESH_TOKEN_KEY] ?: "",
                isLogin = preferences[IS_LOGIN_KEY] ?: false,
                username = preferences[NAME_KEY] ?: "No Name",
                password = preferences[PASSWORD_KEY] ?: "No Password",
                phone = preferences[PHONE_KEY],
                photoUrl = preferences[PHOTO_URL_KEY]
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
        private val NAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val PHONE_KEY = stringPreferencesKey("phone")
        private val PHOTO_URL_KEY = stringPreferencesKey("photo_url")

        fun getInstance(context: Context): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(context.dataStore, context.persistentDataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
