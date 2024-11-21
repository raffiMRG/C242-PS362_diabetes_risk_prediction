package com.capstone.diabticapp.ui.account

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Contoh data profil TODO: nanti gunain data dari API sesuai yang sudah teregister oleh user
        val accountItems = listOf(
            AccountItem("Nama", "Julia Mario"),
            AccountItem("Email", "juliamario@mail.com"),
            AccountItem("No.HP", "085859028141")
        )

        val adapter = AccountProfileAdapter(accountItems) { accountItem ->
            when (accountItem.label) {
                "Nama" -> navigateToFragment(ChangeNameFragment())
                "Email" -> navigateToFragment(ChangePasswordFragment()) // Example placeholder
                "No.HP" -> navigateToFragment(ChangePhoneFragment())
            }
        }

        binding.recyclerView.adapter = adapter
        // Add back stack listener to handle visibility toggling
        supportFragmentManager.addOnBackStackChangedListener {
            handleBackStackChanged()
        }

    }

    // TODO: untuk navigasi ke Fragment yang bisa merubah nama profile saat ini
    private fun navigateToFragment(fragment: Fragment) {
        toggleVisibility(true)
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }
    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun toggleVisibility(showFragment: Boolean) {
        binding.accountContent.visibility = if (showFragment) View.GONE else View.VISIBLE
        binding.fragmentContainer.visibility = if (showFragment) View.VISIBLE else View.GONE
    }
    private fun handleBackStackChanged() {
        // Check the back stack and toggle visibility accordingly
        val isFragmentVisible = supportFragmentManager.backStackEntryCount > 0
        toggleVisibility(showFragment = isFragmentVisible)
    }

}