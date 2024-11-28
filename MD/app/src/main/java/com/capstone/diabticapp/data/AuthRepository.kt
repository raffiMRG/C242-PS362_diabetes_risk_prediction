package com.capstone.diabticapp.data

import com.capstone.diabticapp.data.pref.UserModel
import com.capstone.diabticapp.data.pref.UserPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await

class AuthRepository(private val userPreference: UserPreference) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    suspend fun loginWithGoogle(idToken: String): Result<FirebaseUser?> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user
            user?.let {
                userPreference.saveSession(
                    UserModel(
                        name = it.displayName ?: "No Name",
                        email = it.email ?: "",
                        token = it.uid,
                        isLogin = true,
                        photoUrl = it.photoUrl?.toString()
                    )
                )
            }
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getUserSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        firebaseAuth.signOut()
        println("FirebaseAuth: User signed out")
        userPreference.logout()
    }
}
