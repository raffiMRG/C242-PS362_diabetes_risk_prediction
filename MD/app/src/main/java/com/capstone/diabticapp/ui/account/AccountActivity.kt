package com.capstone.diabticapp.ui.account

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityAccountBinding
import com.capstone.diabticapp.ui.custom.ProfileAppBar
import com.capstone.diabticapp.utils.ImageHelper

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var appBar: ProfileAppBar
    private val accountViewModel: AccountViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { handleImageUri(it) }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBar = binding.appbar
        appBar.setTitle(getString(R.string.profile))

        observeViewModel()
        setupListeners()
        changeProfile()


        appBar.setSaveClickListener {
            saveChanges()
        }

        appBar.setCancelClickListener {
            cancelChanges()
        }

        appBar.setBackClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }

    private fun changeProfile(){
        binding.tvChangeEmail.setOnClickListener {
            enterEditMode("Edit Email", binding.etEmail)
        }

        binding.tvChangePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvChangePhone.setOnClickListener {
            enterEditMode("Edit Phone Number", binding.etPhone)
        }

    }
    private fun setupListeners() {
        binding.ivEditPicture.setOnClickListener {
            pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun handleImageUri(uri: Uri) {
        val multipartBody = ImageHelper.createMultipartBody(this, uri, "profilePicture")
        if (multipartBody != null) {
            accountViewModel.uploadProfilePicture(multipartBody)
        } else {
            Toast.makeText(this, "Failed to process image", Toast.LENGTH_SHORT).show()
        }
    }


    @Suppress("DEPRECATION")
    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            accountViewModel.userName.collect { name ->
                binding.etName.setText(name)
            }
        }

        lifecycleScope.launchWhenStarted {
            accountViewModel.userEmail.collect { email ->
                binding.etEmail.setText(email)
            }
        }

        lifecycleScope.launchWhenStarted {
            accountViewModel.userPhone.collect { phone ->
                binding.etPhone.setText(phone)
            }
        }

        lifecycleScope.launchWhenStarted {
            accountViewModel.stateMessage.collect { message ->
                message?.let {
                    Toast.makeText(this@AccountActivity, it, Toast.LENGTH_SHORT).show()
                    accountViewModel.clearMessage()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            accountViewModel.userPhotoUrl.collect { photoUrl ->
                Glide.with(this@AccountActivity)
                    .load(photoUrl)
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .circleCrop()
                    .into(binding.ivProfilePicture)
            }
        }

        lifecycleScope.launchWhenStarted {
            accountViewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun enterEditMode(title: String, editText: View) {
        appBar.setTitleText(title)
        appBar.showEditActions()

        editText.isFocusableInTouchMode = true
        editText.isFocusable = true
        editText.requestFocus()
    }

    private fun saveChanges() {
        val newPhone = binding.etPhone.text.toString()
        val newEmail = binding.etEmail.text.toString()

        println("AppBar Title: ${appBar.title}")

        when (appBar.getTitleText()) {
            "Edit Email" -> {
                accountViewModel.changeEmail(newEmail)
                println("Changing name to: $newEmail")
            }
            "Edit Phone Number" -> {
                accountViewModel.changePhone(newPhone)
                println("Changing phone to: $newPhone")
            }
            else -> println("No matching title found for action")
        }
        exitEditMode()
    }

    private fun cancelChanges() {
        observeViewModel()
        binding.etPhone.setText(accountViewModel.userPhone.value ?: "")
        binding.etEmail.setText(accountViewModel.userEmail.value ?: "")
        exitEditMode()
    }

    private fun exitEditMode() {
        appBar.setTitleText(getString(R.string.profile)) // Reset app bar title
        appBar.hideEditActions() // Hide save and cancel buttons
        disableEditing(binding.etPhone)
        disableEditing(binding.etEmail)
    }

    private fun disableEditing(editText: View) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
    }
}
