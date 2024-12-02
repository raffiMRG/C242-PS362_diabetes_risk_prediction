package com.capstone.diabticapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangePhoneResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
