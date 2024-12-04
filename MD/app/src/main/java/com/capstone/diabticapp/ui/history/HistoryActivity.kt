package com.capstone.diabticapp.ui.history

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.HistoryViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityHistoryBinding
import com.capstone.diabticapp.helper.NetworkUtils
import com.capstone.diabticapp.ui.news.ExampleNews
import com.capstone.diabticapp.ui.news.NewsAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory.getInstance(this)
    }
    private lateinit var networkStatusHelper: NetworkUtils
    private var internetStatus: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        networkStatusHelper = NetworkUtils(this)

        setupObservers()

        viewModel.statusMesage.observe(this){ message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Observasi status internet secara real-time
        networkStatusHelper.observe(this) { isConnected ->
            if (isConnected) {
                internetStatus = true
                viewModel.getDataPrediction(forceRefresh = internetStatus)
            } else {
                internetStatus = false
                viewModel.getDataPrediction(forceRefresh = internetStatus)
            }
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(this) { status ->
            isLoading(status)
        }

        viewModel.getDataResponse.observe(this) { users ->
            if (users != null) {
                val sortedItems = users.reversed()
                sortedItems.forEach { Log.d("sortedData", it.createdAt) }
                val adapter = HistoryAdapter(sortedItems)

                binding.rvHistory.layoutManager = GridLayoutManager(this@HistoryActivity, 2)
                binding.rvHistory.adapter = adapter
            }
        }
    }

    private fun isLoading(status: Boolean){
        if (status){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }
}