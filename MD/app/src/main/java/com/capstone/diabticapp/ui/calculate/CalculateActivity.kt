package com.capstone.diabticapp.ui.calculate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityCalculateBinding

class CalculateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        myAppBar.setOnBackPressedListener{
//            onBackPressed()
//        }
    }
}