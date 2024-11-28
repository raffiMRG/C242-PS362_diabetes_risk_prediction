package com.capstone.diabticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

//
    private fun setupGoogleSignInClient() {
        val clientId = getClientIdFromProperties()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setupClickListeners() {
        // bisa pilih email tiap relog
        binding.btnLoginGoogle.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
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

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { idToken ->
                    authViewModel.loginWithGoogle(idToken)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

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
