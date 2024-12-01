package com.capstone.diabticapp.data.pref

import com.capstone.diabticapp.data.remote.response.Data

//bisa ditambahin kayak isDiabetes buat check apakah user diabetes atau tidak
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
        fun fromLoginResponse(username: String, data: Data): UserModel {
            return UserModel(
                email = username,
                token = data.accessToken ?: "",
                refreshToken = data.refreshToken ?: "",
                isLogin = true,
                username = username,
                photoUrl = null
            )
        }
    }
}