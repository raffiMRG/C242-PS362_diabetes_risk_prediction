package com.capstone.diabticapp.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val refreshToken: String,
    val isLogin: Boolean,
    val username: String,
    val photoUrl: String? = null
)