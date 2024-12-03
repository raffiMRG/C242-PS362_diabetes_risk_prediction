package com.capstone.diabticapp.ui.calculate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.CalculateRepository
import com.capstone.diabticapp.data.remote.request.CalculateRequest
import com.capstone.diabticapp.data.remote.response.CalculateResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class CalculateViewModel(private val calculateRepository: CalculateRepository) : ViewModel() {


    private val _uploadResponse = MutableLiveData<CalculateResponse>()
    val uploadResponse: LiveData<CalculateResponse> = _uploadResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadPrediction(
        data: CalculateRequest
    ) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                withTimeout(60000L) {
                    val response = calculateRepository.uploadPrediction(data)
                    _uploadResponse.value = response
                }
            } catch (e: Exception){
                e.printStackTrace()
                Log.e("errorUpdate", e.message.toString())
            }finally {
                _isLoading.value = false
            }
        }
    }
}