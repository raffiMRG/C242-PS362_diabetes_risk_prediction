package com.capstone.diabticapp.ui.history

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityHistoryBinding
import com.capstone.diabticapp.ui.news.ExampleNews
import com.capstone.diabticapp.ui.news.NewsAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exampleHistory = ExampleHistory.getDefaultInstance()

        exampleHistory.forEach{ Log.d("data", "date : ${it.date} \n status : ${it.status}")}
        val adapter = HistoryAdapter(exampleHistory)

        binding.rvHistory.layoutManager = GridLayoutManager(this@HistoryActivity, 2)
        binding.rvHistory.adapter = adapter
    }
}