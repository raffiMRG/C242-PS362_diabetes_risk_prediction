package com.capstone.diabticapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(

    @field:SerializedName("data")
	val data: AccessTokenData? = null,

    @field:SerializedName("success")
	val success: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)

data class AccessTokenData(

	@field:SerializedName("accessToken")
	val accessToken: String? = null
)
