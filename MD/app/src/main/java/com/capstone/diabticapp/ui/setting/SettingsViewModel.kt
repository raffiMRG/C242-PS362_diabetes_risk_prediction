package com.capstone.diabticapp.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel (private val authRepository: AuthRepository) : ViewModel() {

    val username: LiveData<String> = authRepository.getUserSession().map { session ->
        session.username
    }.asLiveData()

    val userPhotoUrl: LiveData<String?> = authRepository.getUserSession().map { session ->
        session.photoUrl
    }.asLiveData()

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}