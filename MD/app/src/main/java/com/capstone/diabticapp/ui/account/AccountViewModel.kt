package com.capstone.diabticapp.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

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

    fun uploadProfilePicture(file: MultipartBody.Part) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = authRepository.editProfilePicture(file)
                if (response.success == true) {
                    _stateMessage.value = "Profile picture updated successfully!"
                    loadUserInfo()
                } else {
                    _stateMessage.value = response.message ?: "Failed to update profile picture."
                }
            } catch (e: Exception) {
                _stateMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
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
