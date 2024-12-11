package com.capstone.diabticapp.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.remote.response.ChangePasswordResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val authRepository: AuthRepository): ViewModel() {
    private val _stateMessage = MutableStateFlow<String?>(null)
    val stateMessage: StateFlow<String?> = _stateMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun changePassword(newPassword: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response: ChangePasswordResponse = authRepository.changePassword("password", newPassword)
                if (response.success == true) {
                    authRepository.clearUserData()
                    _stateMessage.value = "Password updated successfully! Please log in again."
                } else {
                    _stateMessage.value = response.message ?: "Failed to update password."
                }
            } catch (e: Exception) {
                _stateMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _stateMessage.value = null
    }
}