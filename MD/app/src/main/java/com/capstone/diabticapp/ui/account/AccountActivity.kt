package com.capstone.diabticapp.ui.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.pref.dataStore
import com.capstone.diabticapp.databinding.ActivityAccountBinding
import com.capstone.diabticapp.ui.custom.ProfileAppBar


class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private lateinit var appBar: ProfileAppBar
    private val accountViewModel: AccountViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }

    private var originalName = ""
    private var originalPhone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appBar = binding.appbar
        appBar.setTitle(getString(R.string.profile))

        observeViewModel()

        binding.tvChangeName.setOnClickListener {
            enterEditMode("Edit Name", binding.etName)
        }

        appBar.setSaveClickListener {
            saveChanges()
        }

        appBar.setCancelClickListener {
            cancelChanges()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launchWhenStarted {
            accountViewModel.userName.collect { name ->
                originalName = name
                binding.etName.setText(name)
            }
        }

        lifecycleScope.launchWhenStarted {
            accountViewModel.userEmail.collect { email ->
                binding.tvEmail.text = email
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
        appBar.setTitle(title)
        appBar.showEditActions()

        editText.isFocusableInTouchMode = true
        editText.isFocusable = true
        editText.requestFocus()
    }

    private fun saveChanges() {
        val newName = binding.etName.text.toString()

//        if (newName != originalName) {
//            accountViewModel.updateUserName(newName)
//        }

        exitEditMode()
    }

    private fun cancelChanges() {
        binding.etName.setText(originalName)
        exitEditMode()
    }

    private fun exitEditMode() {
        appBar.setTitle(getString(R.string.profile))
        appBar.hideEditActions()

        disableEditing(binding.etName)
    }

    private fun disableEditing(editText: View) {
        editText.isFocusable = false
        editText.isFocusableInTouchMode = false
    }
}
