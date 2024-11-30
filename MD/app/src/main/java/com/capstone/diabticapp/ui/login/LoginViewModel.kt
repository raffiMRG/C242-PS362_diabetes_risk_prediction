package com.capstone.diabticapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private fun saveSession(user: UserModel){
        viewModelScope.launch {
            authRepository.saveSession(user)
        }
    }

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(username, password)
                if (response.success == true) {
                    response.data?.let { data ->
                        val user = UserModel(
                            email = username,
                            token = data.accessToken ?: "",
                            refreshToken = data.refreshToken ?: "",
                            isLogin = true,
                            username = username,
                            photoUrl = null
                        )
                        saveSession(user)
                        onResult(true)
                    }
                }else{
                    Log.e("LoginViewModel", "Login failed: ${response.message}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during login: ${e.message}")
                onResult(false)
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