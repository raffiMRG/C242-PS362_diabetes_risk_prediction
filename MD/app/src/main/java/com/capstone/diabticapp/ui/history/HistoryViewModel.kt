package com.capstone.diabticapp.ui.history

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.HistoryRepository
import com.capstone.diabticapp.data.database.UserEntity
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    private val _getDataResponse = MutableLiveData<List<UserEntity>>()
    val getDataResponse: LiveData<List<UserEntity>> get() = _getDataResponse

    private val _statusMesage = MutableLiveData<String>()
    val statusMesage: LiveData<String> = _statusMesage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getDataPrediction(forceRefresh: Boolean = false) {
        _isLoading.value = true
        viewModelScope.launch {
            val data = if (forceRefresh) {
                try {
                    Log.d("ModelSource", "Load From API Server")
                    _statusMesage.value = "Get Data From Server"
                    repository.refreshUsers() // Refresh data from API
                }catch (e: Exception){
                    Log.d("ModelSource", "Load room")
//                    Log.e("ExceptionMessage", e.message.toString(), )
                    _statusMesage.value = "Internet Accessed But Failed to Fetch Data, Get Local Data"
                    repository.getUsersFromRoom() // Get data from Room
                }
            } else {
                Log.d("ModelSource", "Load room")
                _statusMesage.value = "Get Local Data"
                repository.getUsersFromRoom() // Get data from Room
            }
            _getDataResponse.value = data
            _isLoading.value = false
        }
    }
}