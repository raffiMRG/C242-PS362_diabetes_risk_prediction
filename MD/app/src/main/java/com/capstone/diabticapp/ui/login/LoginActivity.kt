package com.capstone.diabticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.MainActivity
import com.capstone.diabticapp.databinding.ActivityLoginBinding
import com.capstone.diabticapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkLoginStatus() // Perform login status check
    }

    private fun checkLoginStatus() {
        loginViewModel.checkLoginStatus { isLoggedIn ->
            if (isLoggedIn) {
                // If logged in, directly go to MainActivity
                navigateToMainActivity()
            } else {
                // If not logged in, inflate the login screen
                setupLoginScreen()
            }
        }
    }

    private fun setupLoginScreen() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                showToast("Username and password cannot be empty")
                return@setOnClickListener
            }

            loginViewModel.login(username, password) { isSuccess ->
                if (isSuccess) {
                    showToast("Login Successful!")
                    navigateToMainActivity()
                } else {
                    showToast("Login failed! Please try again.")
                }
            }
        }

        binding.signupOption.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish LoginActivity to prevent returning to it
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
