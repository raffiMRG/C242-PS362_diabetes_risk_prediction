package com.capstone.diabticapp.data

import com.capstone.diabticapp.data.remote.request.CalculateRequest
import com.capstone.diabticapp.data.remote.response.CalculateResponse
import com.capstone.diabticapp.data.remote.retrofit.ApiService

class CalculateRepository private constructor(
    private val apiService: ApiService
) {
    suspend fun uploadPrediction(
        data: CalculateRequest
    ): CalculateResponse {
        return apiService.createPrediction( data )
    }

    companion object {
        @Volatile
        private var instance: CalculateRepository? = null

        fun getInstance(apiService: ApiService): CalculateRepository =
            instance ?: synchronized(this) {
                instance ?: CalculateRepository(apiService)
            }.also { instance = it }
    }
}