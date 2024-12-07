package com.capstone.diabticapp.data

import com.capstone.diabticapp.data.database.UserDao
import com.capstone.diabticapp.data.database.UserEntity
import com.capstone.diabticapp.data.remote.retrofit.ApiService

class HistoryRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    suspend fun getUsersFromRoom(name: String): List<UserEntity> {
        return userDao.getAllUsers(name)
    }

    suspend fun refreshUsers(name: String): List<UserEntity> {
            // Fetch data from API
            val apiResponse = apiService.getAllData()

            // Convert API response to Room-compatible entity
            val userEntities = apiResponse.data.map {
                UserEntity(
                    id = it.id,
                    name = name,
                    riskScore = it.riskScore,
                    classification = it.classification,
                    createdAt = it.createdAt
                )
            }

            // Update Room database
            userDao.deleteAll(name)
            userDao.insertAll(userEntities)

            return userEntities
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null

        fun getInstance(apiService: ApiService, userDao: UserDao): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(apiService, userDao)
            }.also { instance = it }
    }
}