package com.capstone.diabticapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.ui.account.AccountViewModel
import com.capstone.diabticapp.ui.login.ChooseLoginViewModel
import com.capstone.diabticapp.ui.setting.SettingsViewModel

class AuthViewModelFactory(
    private val authRepository: AuthRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ChooseLoginViewModel::class.java) -> {
                ChooseLoginViewModel(authRepository) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}