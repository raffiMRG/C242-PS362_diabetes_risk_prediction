package com.capstone.diabticapp.ui.history

import android.content.Intent
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
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.HistoryViewModelFactory
import com.capstone.diabticapp.MainActivity
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.database.UserEntity
import com.capstone.diabticapp.databinding.ActivityHistoryBinding
import com.capstone.diabticapp.databinding.EmptyStateMedicalHistoryBinding
import com.capstone.diabticapp.helper.NetworkUtils
import com.capstone.diabticapp.ui.home.HomeViewModel
import com.capstone.diabticapp.ui.news.ExampleNews
import com.capstone.diabticapp.ui.news.NewsAdapter

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory.getInstance(this)
    }
    private val userViewModel: HomeViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }
    private lateinit var networkStatusHelper: NetworkUtils
    private var internetStatus: Boolean = true
    private var name: String = "guest"

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
                viewModel.getDataPrediction(name, forceRefresh = internetStatus)
            } else {
                internetStatus = false
                viewModel.getDataPrediction(name, forceRefresh = internetStatus)
            }
        }
    }

    private fun observeViewModel() {
        userViewModel.username.observe(this) { userName ->
            name = userName
            Log.d("userName", "name : $name")
        }
    }

    private fun setupObservers() {
        observeViewModel()
        viewModel.isLoading.observe(this) { status ->
            isLoading(status)
        }

        viewModel.getDataResponse.observe(this) { users ->
            if (users.isNullOrEmpty()) {
                showEmptyStateLayout()
            } else {
                showRecyclerView(users)
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

    private fun showRecyclerView(users: List<UserEntity>) {
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = HistoryAdapter(users)
        binding.rvHistory.layoutManager = GridLayoutManager(this@HistoryActivity, 2)
        binding.rvHistory.adapter = adapter
    }


    private fun showEmptyStateLayout() {
        val binding = EmptyStateMedicalHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddMedicalHistory.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // Replace with appropriate Activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}