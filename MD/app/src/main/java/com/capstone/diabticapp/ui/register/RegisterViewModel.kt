package com.capstone.diabticapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun register(username: String, password: String, email: String, phone: String){
        viewModelScope.launch {

        }

    }
}