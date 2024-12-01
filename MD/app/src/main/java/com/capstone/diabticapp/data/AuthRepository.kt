package com.capstone.diabticapp.data

import com.capstone.diabticapp.data.pref.UserModel
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.remote.request.LoginRequest
import com.capstone.diabticapp.data.remote.request.RegisterRequest
import com.capstone.diabticapp.data.remote.response.GetAccResponse
import com.capstone.diabticapp.data.remote.response.LoginResponse
import com.capstone.diabticapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow


class AuthRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
){
    suspend fun register(username: String, password: String, email: String, phone: String) = apiService.register(
        RegisterRequest(username, password, email, phone)
    )

    suspend fun login(username: String, password: String): LoginResponse {
        val response = apiService.login(LoginRequest(username, password))
        if (response.success == true) {
            response.data?.let { data ->
                val user = UserModel.fromLoginResponse(username, data)
                saveSession(user)

                try {
                    val accountResponse = getAccountData()
                    if (accountResponse.success == true) {
                        accountResponse.data?.let { accountData ->
                            val updatedUser = user.copy(
                                email = accountData.email ?: user.email,
                                username = accountData.username ?: user.username,
                                phone = accountData.phone ?: user.phone,
                                photoUrl = accountData.profilePicture?.toString()
                            )
                            saveSession(updatedUser)
                        }
                    }
                } catch (e: Exception) {
                    println("Failed to fetch account data: ${e.message}")
                }
            }
        }
        return response
    }

    suspend fun logout(){
        userPreference.logout()
    }

    suspend fun getAccountData(): GetAccResponse {
        return apiService.getAccount()
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user.copy(isLogin = true))
    }

    fun getUserSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(userPreference: UserPreference, apiService: ApiService): AuthRepository {
            return instance ?: synchronized(this) {
                instance ?: AuthRepository(userPreference, apiService).also { instance = it }
            }
        }
    }
}
