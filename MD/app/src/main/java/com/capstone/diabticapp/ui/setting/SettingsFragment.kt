package com.capstone.diabticapp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.FragmentSettingBinding
import com.capstone.diabticapp.ui.account.AccountActivity

class SettingsFragment : Fragment() {

    private lateinit var password: String
    private lateinit var username: String
    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingsViewModel by viewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val settingItems = listOf(
            SettingItem(R.drawable.ic_account, "Account"),
            SettingItem(R.drawable.ic_logout, "Logout")
        )

        val adapter = SettingAdapter(settingItems) { settingItem ->
            when (settingItem.title) {
                "Account" -> navigateToAccount()
                "Logout" -> navigateToLogout()
            }
        }
        binding.rvMenu.adapter = adapter
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onResume() {
        super.onResume()
    }

    private fun observeViewModel() {
        viewModel.username.observe(viewLifecycleOwner) { name ->
            username = name
            binding.tvNama.text = name
        }

        viewModel.password.observe(viewLifecycleOwner) { pwd ->
            password = pwd
        }

        viewModel.userPhotoUrl.observe(viewLifecycleOwner) { photoUrl ->
            Glide.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.ic_profile)
                .circleCrop()
                .into(binding.ivProfilePicture)
        }

    }

    private fun navigateToAccount() {
        val intent = Intent(this.context, AccountActivity::class.java)
        this.context?.startActivity(intent)
    }

    private fun navigateToLogout() {
        viewModel.logoutClearUserSession()
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()

        requireActivity().finishAffinity()
    }
}
