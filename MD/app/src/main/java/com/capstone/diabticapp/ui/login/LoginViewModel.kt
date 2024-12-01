package com.capstone.diabticapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun saveSession(user: UserModel){
        viewModelScope.launch {
            authRepository.saveSession(user)
        }
    }

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = authRepository.login(username, password)
                if (response.success == true) {
                    authRepository.getAccountData()
                    onResult(true)
                } else {
                    Log.e("LoginViewModel", "Login failed: ${response.message}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during login: ${e.message}")
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun checkLoginStatus(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val userSession = authRepository.getUserSession().first()
            onResult(userSession.isLogin)
        }
    }
}