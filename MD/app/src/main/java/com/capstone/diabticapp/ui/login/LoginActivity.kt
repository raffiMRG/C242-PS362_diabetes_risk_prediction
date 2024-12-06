package com.capstone.diabticapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.MainActivity
import com.capstone.diabticapp.databinding.ActivityLoginBinding
import com.capstone.diabticapp.ui.onboarding.OnboardingActivity
import com.capstone.diabticapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val loginViewModel by viewModels<LoginViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLoginStatus()
        observeViewModel()
    }

    private fun checkLoginStatus() {
        loginViewModel.checkLoginStatus { isLoggedIn ->
            if (isLoggedIn) {
                navigateToMainActivity()
            } else {
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
        binding.apply {
            loginButton.setOnClickListener { handleLogin() }
            signupOption.setOnClickListener { navigateToActivity(RegisterActivity::class.java) }
        }
    }

    private fun handleLogin() {
        val username = binding.usernameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (username.isBlank() || password.isBlank()) {
            showAlertDialog("Login Failed", "Username and password cannot be empty.")
            return
        }

        loginViewModel.login(username, password) { isSuccess ->
            if (isSuccess) {
                isEverLogin()
            } else {
                showAlertDialog("Login Failed", "Invalid username or password. Please try again.")
            }
        }
    }

    private fun isEverLogin(){
        loginViewModel.checkEverLogedin { status ->
            if(status){
                showAlertDialog("Login Successful", "Welcome back!") {
                    navigateToMainActivity()
                }
            }else{
                navigateToOnboardingActivity()
            }
        }
    }

    private fun navigateToMainActivity() {
        navigateToActivity(MainActivity::class.java)
    }

    private fun navigateToOnboardingActivity() {
        navigateToActivity(OnboardingActivity::class.java)
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        Intent(this, activityClass).also {
            startActivity(it)
            finish()
        }
    }

    private fun showAlertDialog(title: String, message: String, onPositiveClick: (() -> Unit)? = null) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { _, _ -> onPositiveClick?.invoke() }
            create()
            show()
        }
    }

    private fun observeViewModel() {
        loginViewModel.isLoading.observe(this) { isLoading ->
            binding.apply {
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                loginButton.isEnabled = !isLoading
            }
        }
    }
}
