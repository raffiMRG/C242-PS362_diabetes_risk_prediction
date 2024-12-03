package com.capstone.diabticapp.ui.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.NewsViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.ArticleRepository
import com.capstone.diabticapp.data.remote.retrofit.ApiConfig
import com.capstone.diabticapp.databinding.ActivityCalculateBinding
import com.capstone.diabticapp.databinding.ActivityNewsBinding
import com.capstone.diabticapp.ui.detail_news.DetailNewsActivity
import com.capstone.diabticapp.ui.home.HomeCardItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        newsViewModel.getArticles()
        observeArticles()
    }

    private fun setupRecyclerView() {
        binding.rvNews.layoutManager = LinearLayoutManager(this)
    }

    @Suppress("DEPRECATION")
    private fun observeArticles() {
        lifecycleScope.launchWhenStarted {
            newsViewModel.articles.collectLatest { response ->
                if (response != null && response.success == true) {
                    val articles = response.data?.filterNotNull() ?: emptyList()
                    val adapter = NewsAdapter(articles) { article ->
                        val intent = Intent(this@NewsActivity, DetailNewsActivity::class.java)
                        intent.putExtra("ARTICLE_ID", article.id)
                        startActivity(intent)
                    }
                    binding.rvNews.adapter = adapter
                } else {
                    Log.e("NewsActivity", "Failed to load articles")
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            newsViewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }
}