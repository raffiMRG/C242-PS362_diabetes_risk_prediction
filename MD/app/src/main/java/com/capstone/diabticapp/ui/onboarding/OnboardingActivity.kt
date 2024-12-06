package com.capstone.diabticapp.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.diabticapp.MainActivity
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityOnboardingBinding
import com.capstone.diabticapp.databinding.ItemImageViewBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOnboardingBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            next.setOnClickListener{
                navigateToActivity(MainActivity::class.java)
            }
            skip1.setOnClickListener{
                navigateToActivity(MainActivity::class.java)
            }
            skip2.setOnClickListener{
                navigateToActivity(MainActivity::class.java)
            }
        }

    }

    private fun navigateToActivity(activityClass: Class<*>) {
        Intent(this, activityClass).also {
            startActivity(it)
            finish()
        }
    }
}