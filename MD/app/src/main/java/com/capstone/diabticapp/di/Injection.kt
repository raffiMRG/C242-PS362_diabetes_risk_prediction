package com.capstone.diabticapp.di

import android.content.Context
import com.capstone.diabticapp.data.ArticleRepository
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.CalculateRepository
import com.capstone.diabticapp.data.HistoryRepository
import com.capstone.diabticapp.data.database.AppDatabase
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.pref.dataStore
import com.capstone.diabticapp.data.remote.retrofit.ApiConfig
import com.capstone.diabticapp.data.remote.retrofit.ApiService

object Injection {
    fun provideUserPreference(context: Context): UserPreference {
        return UserPreference.getInstance(context)
    }

    fun provideApiService(userPreference: UserPreference): ApiService {
        return ApiConfig(userPreference).getApiService()
    }

    fun provideAuthRepository(context: Context): AuthRepository {
        val userPreference = provideUserPreference(context)
        val apiService = provideApiService(userPreference)
        return AuthRepository.getInstance(userPreference, apiService)
    }

    fun provideCalculateRepository(context: Context): CalculateRepository {
        val userPreference = provideUserPreference(context)
        val apiService = ApiConfig(userPreference).getApiService()
        return CalculateRepository.getInstance(apiService)
    }

    fun provideArticleRepository(context: Context): ArticleRepository {
        val userPreference = provideUserPreference(context)
        val apiService = provideApiService(userPreference)
        return ArticleRepository.getInstance(apiService)
    }

    fun provideHistoryRepository(context: Context): HistoryRepository {
        val userPreference = provideUserPreference(context)
        val apiService = ApiConfig(userPreference).getApiService()
        val userDao = AppDatabase.getInstance(context).userDao()
        return HistoryRepository.getInstance(apiService, userDao)
    }
}