package com.capstone.diabticapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.diabticapp.data.AuthRepository
import kotlinx.coroutines.flow.map

class HomeViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val username: LiveData<String> = authRepository.getUserSession().map { session ->
        session.username
    }.asLiveData()
}