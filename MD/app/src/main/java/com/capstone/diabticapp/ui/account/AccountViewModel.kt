package com.capstone.diabticapp.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.diabticapp.data.pref.UserModel
import com.capstone.diabticapp.data.pref.UserPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AccountViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName

    private val _userEmail = MutableStateFlow<String>("")
    val userEmail: StateFlow<String> = _userEmail

    private val _userPhotoUrl = MutableStateFlow<String?>(null)
    val userPhotoUrl: StateFlow<String?> = _userPhotoUrl

    private val _stateMessage = MutableStateFlow<String?>(null)
    val stateMessage: StateFlow<String?> = _stateMessage

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val user = auth.currentUser
            if (user != null) {
                val name = user.displayName ?: "No Name"
                val email = user.email ?: "No Email"
                val photoUrl = user.photoUrl?.toString()

                _userName.value = name
                _userEmail.value = email
                _userPhotoUrl.value = photoUrl

                val session = userPreference.getSession().first()
                if (session.email != email || session.isLogin != true) {
                    userPreference.saveSession(
                        session.copy(
                            email = email,
                            isLogin = true
                        )
                    )
                }
            } else {
                val session = userPreference.getSession().first()
                _userName.value = session.email
                _userEmail.value = session.email
                _stateMessage.value = "No user is logged in."
            }
        }
    }

    fun updateUserName(newName: String) {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()

        viewModelScope.launch {
            try {
                user?.updateProfile(profileUpdates)?.await()
                _userName.value = newName
                _stateMessage.value = "Name updated successfully!"

                val session = userPreference.getSession().first()
                userPreference.saveSession(session.copy(name = newName))
            } catch (e: Exception) {
                _stateMessage.value = "Failed to update name: ${e.message}"
            }
        }
    }

    fun clearMessage() {
        _stateMessage.value = null
    }
}
