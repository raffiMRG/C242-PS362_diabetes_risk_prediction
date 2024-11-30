package com.capstone.diabticapp.di

import android.content.Context
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.pref.dataStore
import com.capstone.diabticapp.data.remote.retrofit.ApiConfig
import com.capstone.diabticapp.data.remote.retrofit.ApiService

object Injection {
    fun provideUserPreference(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }

    fun provideApiService(userPreference: UserPreference): ApiService {
        return ApiConfig(userPreference).getApiService()
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = provideUserPreference(context)
        val apiService = provideApiService(userPreference)
        return AuthRepository.getInstance(userPreference, apiService)
    }
}