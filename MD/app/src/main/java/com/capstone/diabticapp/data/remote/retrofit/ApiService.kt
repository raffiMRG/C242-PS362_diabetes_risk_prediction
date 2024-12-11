package com.capstone.diabticapp.data.remote.retrofit

import com.capstone.diabticapp.data.remote.request.CalculateRequest
import com.capstone.diabticapp.data.remote.request.ChangeProfileRequest
import com.capstone.diabticapp.data.remote.request.LoginRequest
import com.capstone.diabticapp.data.remote.request.LogoutRequest
import com.capstone.diabticapp.data.remote.request.RefreshTokenRequest
import com.capstone.diabticapp.data.remote.request.RegisterRequest
import com.capstone.diabticapp.data.remote.response.ArticleResponse
import com.capstone.diabticapp.data.remote.response.CalculateResponse
import com.capstone.diabticapp.data.remote.response.ChangeEmailResponse
import com.capstone.diabticapp.data.remote.response.ChangePasswordResponse
import com.capstone.diabticapp.data.remote.response.ChangePhoneResponse
import com.capstone.diabticapp.data.remote.response.DetailArticleResponse
import com.capstone.diabticapp.data.remote.response.EditProfilePictureResponse
import com.capstone.diabticapp.data.remote.response.GetAccResponse
import com.capstone.diabticapp.data.remote.response.HistoryResponse
import com.capstone.diabticapp.data.remote.response.LoginResponse
import com.capstone.diabticapp.data.remote.response.LogoutResponse
import com.capstone.diabticapp.data.remote.response.RefreshTokenResponse
import com.capstone.diabticapp.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun login(
       @Body request: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @GET("users/profile")
    suspend fun getAccount(): GetAccResponse

    @Multipart
    @POST("users/profile/profile-picture")
    suspend fun editProfilePicture(
        @Part file: MultipartBody.Part
    ): EditProfilePictureResponse

    @PATCH("users/profile")
    suspend fun changePassword(
        @Body request: ChangeProfileRequest
    ):ChangePasswordResponse

    @PATCH("users/profile")
    suspend fun changePhone(
        @Body request: ChangeProfileRequest
    ): ChangePhoneResponse

    @PATCH("users/profile")
    suspend fun changeEmail(
        @Body request: ChangeProfileRequest
    ):ChangeEmailResponse

    @GET("articles")
    suspend fun getArticles(): ArticleResponse

    @GET("articles/{id}")
    suspend fun getDetailArticle(
        @Path("id") articleId: String
    ): DetailArticleResponse

    @POST("users/predictions")
    suspend fun createPrediction(
        @Body user: CalculateRequest
    ): CalculateResponse

    @GET("users/predictions")
    suspend fun getAllData(): HistoryResponse

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): RefreshTokenResponse

    @POST("auth/logout")
    suspend fun logout(
        @Body request: LogoutRequest
    ): LogoutResponse
}