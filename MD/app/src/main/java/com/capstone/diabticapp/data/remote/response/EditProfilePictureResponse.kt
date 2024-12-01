package com.capstone.diabticapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class EditProfilePictureResponse(

	@field:SerializedName("data")
	val data: EditProfilePictureData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class EditProfilePictureData(

	@field:SerializedName("profilePictureUrl")
	val profilePictureUrl: String? = null
)
