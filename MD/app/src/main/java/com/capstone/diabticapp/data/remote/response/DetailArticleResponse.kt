package com.capstone.diabticapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailArticleResponse(

	@field:SerializedName("data")
	val data: DetailData? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

data class CreatedDateDetail(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class DetailData(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("createdDate")
	val createdDate: CreatedDateDetail? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("updatedDate")
	val updatedDate: UpdatedDateDetail? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class UpdatedDateDetail(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)
