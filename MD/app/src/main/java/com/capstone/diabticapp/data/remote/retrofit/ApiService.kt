package com.capstone.diabticapp.data.remote.retrofit

import com.capstone.diabticapp.data.remote.request.ChangePasswordRequest
import com.capstone.diabticapp.data.remote.request.LoginRequest
import com.capstone.diabticapp.data.remote.request.RegisterRequest
import com.capstone.diabticapp.data.remote.response.ChangePasswordResponse
import com.capstone.diabticapp.data.remote.response.EditProfilePictureResponse
import com.capstone.diabticapp.data.remote.response.GetAccResponse
import com.capstone.diabticapp.data.remote.response.LoginResponse
import com.capstone.diabticapp.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("login")
    suspend fun login(
       @Body request: LoginRequest
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @GET("account")
    suspend fun getAccount(): GetAccResponse

    @Multipart
    @POST("account/edit-profile-picture")
    suspend fun editProfilePicture(
        @Part file: MultipartBody.Part
    ): EditProfilePictureResponse

    @PATCH("account/edit")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    ):ChangePasswordResponse
}