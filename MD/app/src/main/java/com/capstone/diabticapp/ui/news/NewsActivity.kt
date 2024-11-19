package com.capstone.diabticapp.ui.news

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityCalculateBinding
import com.capstone.diabticapp.databinding.ActivityNewsBinding
import com.capstone.diabticapp.ui.home.HomeCardItem

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val exampleNews = ExampleNews.getDefaultInstance()

        exampleNews.forEach{ Log.d("data", "title : ${it.title} \n image : ${it.image}, description : ${it.description}")}
        val adapter = NewsAdapter(exampleNews)

        binding.rvNews.layoutManager = LinearLayoutManager(this@NewsActivity)
        binding.rvNews.adapter = adapter
    }
}