package com.capstone.diabticapp.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AccountViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    private val _userEmail = MutableStateFlow<String>("")
    val userEmail: StateFlow<String> = _userEmail

    private val _userPhone = MutableStateFlow<String>("")
    val userPhone: StateFlow<String> = _userPhone

    private val _userPhotoUrl = MutableStateFlow<String?>(null)
    val userPhotoUrl: StateFlow<String?> = _userPhotoUrl

    private val _stateMessage = MutableStateFlow<String?>(null)
    val stateMessage: StateFlow<String?> = _stateMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val cachedUser = authRepository.getUserSession().first()
            _userName.value = cachedUser.username
            _userEmail.value = cachedUser.email
            _userPhone.value = cachedUser.phone ?: "No Phone"

            try {
                val response = authRepository.getAccountData()
                if (response.success == true) {
                    response.data?.let { data ->
                        val updatedUser = UserModel(
                            email = data.email ?: cachedUser.email,
                            token = cachedUser.token,
                            refreshToken = cachedUser.refreshToken,
                            isLogin = true,
                            username = data.username ?: cachedUser.username,
                            phone = data.phone ?: cachedUser.phone
                        )
                        authRepository.saveSession(updatedUser)
                    }
                } else {
                    _stateMessage.value = response.message ?: "Failed to fetch account data."
                }
            } catch (e: Exception) {
                _stateMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun clearMessage() {
        _stateMessage.value = null
    }
}
