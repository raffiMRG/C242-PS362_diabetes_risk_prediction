package com.capstone.diabticapp.ui.detail_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.ArticleRepository
import com.capstone.diabticapp.data.remote.response.DetailData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailNewsViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _detailArticle = MutableStateFlow<DetailData?>(null)
    val detailArticle: StateFlow<DetailData?> = _detailArticle

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getDetailArticle(articleId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = articleRepository.getDetailArticle(articleId)
                _detailArticle.value = response.data
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to load article"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
