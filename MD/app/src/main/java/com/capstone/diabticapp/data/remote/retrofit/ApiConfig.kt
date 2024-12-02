package com.capstone.diabticapp.data.remote.retrofit

import android.util.Log
import com.capstone.diabticapp.data.pref.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig(private val userPreference: UserPreference) {

    private suspend fun getToken(): String {
        return userPreference.getSession().first().token
    }

    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val token = runBlocking { getToken() }
            Log.d("Token", token)
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Timeout koneksi
            .readTimeout(60, TimeUnit.SECONDS)    // Timeout membaca data
            .writeTimeout(60, TimeUnit.SECONDS)   // Timeout menulis data
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://diabtic-capstone-project.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}