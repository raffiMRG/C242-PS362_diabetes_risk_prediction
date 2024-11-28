package com.capstone.diabticapp.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val name: String = "No Name",
    val photoUrl: String? = null

)