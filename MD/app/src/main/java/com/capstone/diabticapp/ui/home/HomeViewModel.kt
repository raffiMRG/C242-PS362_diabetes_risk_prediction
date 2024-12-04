package com.capstone.diabticapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserPreference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    val username: LiveData<String> = authRepository.getUserSession().map { session ->
        session.username
    }.asLiveData()

    val userPhotoUrl: LiveData<String?> = authRepository.getUserSession().map { session ->
        session.photoUrl
    }.asLiveData()

    private val _isDiabetes = MutableLiveData<Boolean>()
    val isDiabetes: LiveData<Boolean> = _isDiabetes

    init {
        viewModelScope.launch {
            val user = authRepository.getUserSession().first()
            userPreference.getDiabetesStatus(user.username).collect { isDiabetes ->
                _isDiabetes.value = isDiabetes
            }
        }
    }

//    fun updateDiabetesStatus(isDiabetes: Boolean) {
//        viewModelScope.launch {
//            val user = authRepository.getUserSession().first()
//            userPreference.saveDiabetesStatus(isDiabetes, user.username)
//            _isDiabetes.value = isDiabetes
//        }
//    }
}