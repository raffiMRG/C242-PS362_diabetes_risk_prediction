package com.capstone.diabticapp.data.remote.retrofit

import android.util.Log
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.remote.request.RefreshTokenRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.capstone.diabticapp.BuildConfig

class ApiConfig(private val userPreference: UserPreference) {

    private suspend fun getToken(): String {
        return userPreference.getSession().first().token
    }

    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            val token = runBlocking { getToken() }
            Log.d("Token", token)
            val requestWithToken = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            val response = chain.proceed(requestWithToken)

            if (response.code == 500 || response.code == 401) {
                response.close()
                val newAccessToken = runBlocking {
                    val refreshToken = userPreference.getSession().first().refreshToken

                    try {
                        val retrofit = Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        val apiService = retrofit.create(ApiService::class.java)
                        val refreshResponse = apiService.refreshToken(RefreshTokenRequest(refreshToken))

                        if (refreshResponse.success == true) {
                            val newToken = refreshResponse.data?.accessToken ?: ""
                            userPreference.updateAccessToken(newToken)
                            newToken
                        } else {
                            Log.e("RefreshToken", "Refresh token is invalid or expired.")
                            null
                        }
                    } catch (e: Exception) {
                        Log.e("RefreshTokenError", "Error while refreshing token: ${e.message}")
                        null
                    }
                }

                if (newAccessToken != null) {
                    val newRequest = request.newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()
                    return@Interceptor chain.proceed(newRequest)
                }
            }
            return@Interceptor response
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS) // Timeout koneksi
            .readTimeout(60, TimeUnit.SECONDS)    // Timeout membaca data
            .writeTimeout(60, TimeUnit.SECONDS)   // Timeout menulis data
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}
