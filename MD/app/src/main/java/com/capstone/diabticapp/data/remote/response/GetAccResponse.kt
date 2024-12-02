package com.capstone.diabticapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetAccResponse(

	@field:SerializedName("data")
	val data: DataAcc? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataAcc(

	@field:SerializedName("profilePicture")
	val profilePicture: Any? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
