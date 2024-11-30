package com.capstone.diabticapp.data.remote.request

data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String,
    val phone: String,
)
