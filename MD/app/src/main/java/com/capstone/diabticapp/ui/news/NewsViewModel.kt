package com.capstone.diabticapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.ArticleRepository
import com.capstone.diabticapp.data.remote.response.ArticleResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val articleRepository: ArticleRepository): ViewModel() {
    private val _articles = MutableStateFlow<ArticleResponse?>(null)
    val articles: StateFlow<ArticleResponse?> = _articles

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getArticles() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = articleRepository.getArticles()
                _articles.value = response
            } catch (e: Exception) {
                _articles.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

}