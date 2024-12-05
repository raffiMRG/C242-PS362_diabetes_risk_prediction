package com.capstone.diabticapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val loginData: LoginData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class LoginData(

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
