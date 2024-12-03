package com.capstone.diabticapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.diabticapp.data.ArticleRepository
import com.capstone.diabticapp.data.remote.retrofit.ApiConfig
import com.capstone.diabticapp.di.Injection
import com.capstone.diabticapp.ui.detail_news.DetailNewsViewModel
import com.capstone.diabticapp.ui.news.NewsViewModel

class NewsViewModelFactory(private val articleRepository: ArticleRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(articleRepository) as T
            }

            modelClass.isAssignableFrom(DetailNewsViewModel::class.java) -> {
                DetailNewsViewModel(articleRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: NewsViewModelFactory? = null

        fun getInstance(context: Context): NewsViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: NewsViewModelFactory(
                    Injection.provideArticleRepository(context)
                ).also { instance = it }
            }
        }
    }
}