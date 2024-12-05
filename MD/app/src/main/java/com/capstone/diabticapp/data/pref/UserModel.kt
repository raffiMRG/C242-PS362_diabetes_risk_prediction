package com.capstone.diabticapp.data.pref

import com.capstone.diabticapp.data.remote.response.LoginData

data class UserModel(
    val email: String,
    val token: String,
    val refreshToken: String,
    val isLogin: Boolean,
    val username: String,
    val photoUrl: String? = null,
    val phone: String? = null

){
    companion object {
        fun fromLoginResponse(username: String, loginData: LoginData): UserModel {
            return UserModel(
                email = username,
                token = loginData.accessToken ?: "",
                refreshToken = loginData.refreshToken ?: "",
                isLogin = true,
                username = username,
                photoUrl = null
            )
        }
    }
}