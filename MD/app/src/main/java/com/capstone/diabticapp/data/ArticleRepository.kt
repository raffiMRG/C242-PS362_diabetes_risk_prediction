package com.capstone.diabticapp.data

import com.capstone.diabticapp.data.remote.response.ArticleResponse
import com.capstone.diabticapp.data.remote.response.GetAccResponse
import com.capstone.diabticapp.data.remote.retrofit.ApiService

class ArticleRepository private constructor(
    private  val apiService: ApiService
){

    suspend fun getArticles(): ArticleResponse {
        return apiService.getArticles()
    }

    companion object{
        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(apiService: ApiService): ArticleRepository =
            instance?: synchronized(this){
                instance?: ArticleRepository(apiService)
            }.also { instance = it }
    }
}