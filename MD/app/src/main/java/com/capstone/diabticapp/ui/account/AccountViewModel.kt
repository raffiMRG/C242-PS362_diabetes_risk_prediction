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
            _userPhotoUrl.value = cachedUser.photoUrl
        }
    }

    fun clearMessage() {
        _stateMessage.value = null
    }
}
