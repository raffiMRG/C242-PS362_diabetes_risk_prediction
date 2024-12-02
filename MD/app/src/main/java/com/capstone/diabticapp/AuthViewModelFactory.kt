package com.capstone.diabticapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.di.Injection
import com.capstone.diabticapp.ui.account.AccountViewModel
import com.capstone.diabticapp.ui.account.ChangePasswordViewModel
import com.capstone.diabticapp.ui.home.HomeViewModel
import com.capstone.diabticapp.ui.login.LoginViewModel
import com.capstone.diabticapp.ui.register.RegisterViewModel
import com.capstone.diabticapp.ui.setting.SettingsViewModel

class AuthViewModelFactory(
    private val authRepository: AuthRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthViewModelFactory? = null

        fun getInstance(context: Context): AuthViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val userPreference = Injection.provideUserPreference(context)
                val apiService = Injection.provideApiService(userPreference)
                val authRepository = AuthRepository.getInstance(userPreference, apiService)
                AuthViewModelFactory(authRepository).also {
                    INSTANCE = it
                }
            }
        }
    }
}