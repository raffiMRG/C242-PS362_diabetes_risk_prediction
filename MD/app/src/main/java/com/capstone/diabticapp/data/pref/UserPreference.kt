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

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    // Save user session
    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGIN_KEY] = user.isLogin
//            preferences[IS_OTP_VERIFIED_KEY] = user.isOtpVerified
//            preferences[IS_PHONE_NUMBER_SET_KEY] = user.isPhoneNumberSet
//            preferences[PHONE_NUMBER_KEY] = user.phoneNumber?: ""
        }
    }

    // Retrieve user session
    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                email = preferences[EMAIL_KEY] ?: "",
                token = preferences[TOKEN_KEY] ?: "",
                isLogin = preferences[IS_LOGIN_KEY] ?: false
//                isOtpVerified = preferences[IS_OTP_VERIFIED_KEY] ?: false,
//                isPhoneNumberSet = preferences[IS_PHONE_NUMBER_SET_KEY] ?: false,
//                phoneNumber = preferences[PHONE_NUMBER_KEY]
            )
        }
    }

    // Clear session on logout
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

//    // Utility to check if the user is logged in
//    fun isLoggedIn(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[IS_LOGIN_KEY] ?: false
//        }
//    }
//
//    // Utility to check if the user has verified OTP
//    fun isOtpVerified(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[IS_OTP_VERIFIED_KEY] ?: false
//        }
//    }
//
//    // Utility to check if the user has set a phone number
//    fun isPhoneNumberSet(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[IS_PHONE_NUMBER_SET_KEY] ?: false
//        }
//    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")
//        private val IS_OTP_VERIFIED_KEY = booleanPreferencesKey("isOtpVerified")
//        private val IS_PHONE_NUMBER_SET_KEY = booleanPreferencesKey("isPhoneNumberSet")
//        private val PHONE_NUMBER_KEY = stringPreferencesKey("phoneNumber")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
