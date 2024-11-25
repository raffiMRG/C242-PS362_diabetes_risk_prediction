package com.capstone.diabticapp.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.AuthRepository
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.pref.dataStore
import com.capstone.diabticapp.databinding.FragmentSettingBinding
import com.capstone.diabticapp.ui.account.AccountActivity
import com.capstone.diabticapp.ui.login.LoginGmailActivity

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val viewModel: SettingsViewModel by viewModels {
        AuthViewModelFactory(AuthRepository(UserPreference.getInstance(requireContext().dataStore)))
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

    private fun navigateToAccount() {
        val intent = Intent(this.context, AccountActivity::class.java)
        this.context?.startActivity(intent)
    }


    private fun navigateToLogout() {
        viewModel.logout()
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Navigate to ChooseLoginActivity
        val intent = Intent(requireContext(), LoginGmailActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Clear the back stack
    }
}
