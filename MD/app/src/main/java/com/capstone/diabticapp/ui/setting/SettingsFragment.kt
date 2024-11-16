package com.capstone.diabticapp.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabeticapp.ui.setting.SettingAdapter
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.FragmentSettingBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Define the menu items
        val settingItems = listOf(
            SettingItem(R.drawable.ic_account, "Account"),
            SettingItem(R.drawable.ic_appearance, "Appearance"),
            SettingItem(R.drawable.ic_privacy, "Privacy & Security")
        )

        // Set up the adapter and RecyclerView
        val adapter = SettingAdapter(settingItems) { settingItem ->
            // Handle menu item click
            when (settingItem.title) {
                "Account" -> navigateToAccount()
                "Appearance" -> navigateToAppearance()
                "Privacy & Security" -> navigateToPrivacy()
            }
        }
        binding.rvMenu.adapter = adapter
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToAccount() {
        // Handle navigation to the Account screen
    }

    private fun navigateToAppearance() {
        // Handle navigation to the Appearance screen
    }

    private fun navigateToPrivacy() {
        // Handle navigation to the Privacy & Security screen
    }
}
