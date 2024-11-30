package com.capstone.diabticapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.AuthRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(username: String, password: String, email: String, phone: String, onResult: (Boolean) -> Unit){
        viewModelScope.launch {
            _isLoading.value = true
            try{
                val response = authRepository.register(username, password, email, phone)
                if(response.success == true){
                    onResult(true)
                }else{
                    onResult(false)
                }
            }catch (e: Exception){
                e.printStackTrace()
                onResult(false)
            }finally {
                _isLoading.value = false
            }
        }
    }
}