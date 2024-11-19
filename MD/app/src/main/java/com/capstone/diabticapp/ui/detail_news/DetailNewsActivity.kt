package com.capstone.diabticapp.ui.detail_news

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityDetailNewsBinding

class DetailNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // Tangkap data dari Intent
        val image = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")

        Glide.with(binding.root)
            .load(image)
            .into(binding.img)
        binding.title.text = title
        binding.desc.text = description
    }
}