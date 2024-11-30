package com.capstone.diabticapp.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.databinding.ActivityRegisterBinding
import com.capstone.diabticapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel by viewModels<RegisterViewModel> {
        AuthViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        hasAccount()
        observeViewModel()
    }

    private fun setupListener() {
        binding.signUpButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneNumberEditText.text.toString()

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                showAlertDialog("Pendaftaran Gagal", "Mohon isi semua field.")
                return@setOnClickListener
            }

            registerViewModel.register(username, password, email, phone) { success ->
                if (success) {
                    showAlertDialog("Pendaftaran Berhasil", "Akun Anda berhasil dibuat!") {
                        navigateToLogin()
                    }
                } else {
                    showAlertDialog("Pendaftaran Gagal", "Terjadi kesalahan saat mendaftar. Silakan coba lagi.")
                }
            }
        }
    }

    private fun observeViewModel() {
        registerViewModel.isLoading.observe(this) { isLoading ->
            binding.apply {
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                signUpButton.isEnabled = !isLoading
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun hasAccount() {
        binding.loginOption.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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
}