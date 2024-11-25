package com.capstone.diabticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.MainActivity
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.pref.dataStore
import com.capstone.diabticapp.databinding.ActivityLoginGmailBinding
import com.capstone.diabticapp.ui.otp.OtpVerificationActivity
import com.capstone.diabticapp.ui.otp.PhoneNumberActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import java.util.Properties

@Suppress("DEPRECATION")
class LoginGmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginGmailBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val authViewModel: ChooseLoginViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(UserPreference.getInstance(applicationContext.dataStore)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToHome()
            return
        }

        setupGoogleSignInClient()
        setupClickListeners()
        observeLoginState()
    }

//    private fun checkUserSession() {
//        lifecycleScope.launchWhenStarted {
//            val userSession = authViewModel.getUserSession().first()
//
//            when {
//                userSession.isLogin && userSession.isPhoneNumberSet && userSession.isOtpVerified -> {
//                    // User fully verified, navigate to MainActivity
//                    navigateToActivity(MainActivity::class.java)
//                }
//                userSession.isLogin && !userSession.isPhoneNumberSet -> {
//                    // User logged in but phone number is not set
//                    navigateToActivity(PhoneNumberActivity::class.java)
//                }
//                userSession.isLogin && !userSession.isOtpVerified -> {
//                    // User logged in but OTP is not verified
//                    navigateToActivity(OtpVerificationActivity::class.java)
//                }
//                else -> {
//                    // User not logged in
//                    navigateToActivity(LoginGmailActivity::class.java)
//                }
//
//            }
//        }
//    }

    private fun setupGoogleSignInClient() {
        val clientId = getClientIdFromProperties()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupClickListeners() {
        // kalo relogin user gak bisa pilih email baru lagi
//        binding.btnLoginGoogle.setOnClickListener {
//            val signInIntent = googleSignInClient.signInIntent
//            startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
//        }
        // bisa pilih email tiap relog
        binding.btnLoginGoogle.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
            }
        }

        binding.btnLoginEmail.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launchWhenStarted {
            authViewModel.loginState.collect { state ->
                when (state) {
                    is AuthState.Idle -> { }
                    is AuthState.Loading -> {
                        Toast.makeText(this@LoginGmailActivity, "Logging in...", Toast.LENGTH_SHORT).show()
                    }
                    is AuthState.Success -> {
                        Toast.makeText(this@LoginGmailActivity, "Login Successful!", Toast.LENGTH_SHORT).show()
                        navigateToHome()
                    }
                    is AuthState.Error -> {
                        Toast.makeText(this@LoginGmailActivity, "Error: ${state.errorMessage}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.result
            account?.idToken?.let {
                authViewModel.loginWithGoogle(it)
            } ?: run {
                Toast.makeText(this, "Google sign-in failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun navigateToActivity(activity: Class<*>) {
//        val intent = Intent(this, activity)
//        startActivity(intent)
//        finish()
//    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Masih bug karena belom ada API auth dari CC klo klik login bisa langsung masuk home tanpa harus punya akun
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 100
    }

    private fun getClientIdFromProperties(): String {
        val properties = Properties()
        try {
            val inputStream = resources.openRawResource(R.raw.config)
            properties.load(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return properties.getProperty("DEFAULT_WEB_CLIENT_ID", "")
    }

}
