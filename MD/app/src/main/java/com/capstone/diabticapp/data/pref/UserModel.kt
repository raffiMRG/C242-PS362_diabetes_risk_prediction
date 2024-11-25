package com.capstone.diabticapp.data.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
//    val isOtpVerified: Boolean = false,
//    val isPhoneNumberSet: Boolean = false,
//    val phoneNumber: String? = null
)