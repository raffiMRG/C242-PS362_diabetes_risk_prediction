package com.capstone.diabticapp.ui.setting

import android.util.Log
import androidx.datastore.preferences.protobuf.Internal.BooleanList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel (private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val username: LiveData<String> = authRepository.getUserSession().map { session ->
        session.username
    }.asLiveData()

    val password: LiveData<String> = authRepository.getUserSession().map { session ->
        session.password
    }.asLiveData()

    val userPhotoUrl: LiveData<String?> = authRepository.getUserSession().map { session ->
        session.photoUrl
    }.asLiveData()

    fun logout(uname:String, pwd: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            //            authRepository.logout()
            try {
                _isLoading.value = true
                val response = authRepository.logout(uname, pwd)
                if (response.success == true){
                    authRepository.clearUserData()
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                Log.e("ErrorLogout", e.message.toString())
                _isLoading.value = false
                onResult(false)
            }finally {
                _isLoading.value = false
                authRepository.clearUserData()
            }
        }
    }

    fun logoutClearUserSession() {
        viewModelScope.launch {
            authRepository.logoutClearUserSession()
        }
    }
}