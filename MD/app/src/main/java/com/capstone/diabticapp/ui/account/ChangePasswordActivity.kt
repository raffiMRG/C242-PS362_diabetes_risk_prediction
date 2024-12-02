package com.capstone.diabticapp.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityChangePasswordBinding
import com.capstone.diabticapp.ui.login.LoginActivity

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val viewModel: ChangePasswordViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()

    }

    private fun setupListeners() {
        binding.buttonSave.setOnClickListener {
            val newPassword = binding.passwordEditTextAcc.text.toString()

            if (newPassword.isEmpty()) {
                binding.passwordEditTextAcc.error = "Password cannot be empty"
                return@setOnClickListener
            }

            viewModel.changePassword(newPassword)
        }

        binding.appbar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @Suppress("DEPRECATION")
    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateMessage.collect { message ->
                message?.let {
                    Toast.makeText(this@ChangePasswordActivity, it, Toast.LENGTH_SHORT).show()
                    if (it.contains("successfully", true)) {
                        navigateToLogin()
                    }
                    viewModel.clearMessage()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect { isLoading ->
                binding.buttonSave.isEnabled = !isLoading
            }
        }
    }

    private fun navigateToLogin() {
        // Navigate to login screen after logout
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}