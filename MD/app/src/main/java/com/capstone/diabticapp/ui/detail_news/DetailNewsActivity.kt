package com.capstone.diabticapp.ui.detail_news

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.capstone.diabticapp.NewsViewModelFactory
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ActivityDetailNewsBinding
import com.capstone.diabticapp.ui.custom.NewsAppBar
import com.capstone.diabticapp.utils.Time.formatDate
import kotlinx.coroutines.flow.collectLatest

class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding

    private val detailNewsViewModel: DetailNewsViewModel by viewModels {
        NewsViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsAppBar: NewsAppBar = findViewById(R.id.customAppBar)
        newsAppBar.setAppBarTitle("News")

        val articleId = intent.getStringExtra("ARTICLE_ID")
        if (articleId != null) {
            observeDetailArticle()
            detailNewsViewModel.getDetailArticle(articleId)
        } else {
            Toast.makeText(this, "Invalid article ID", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    @Suppress("DEPRECATION")
    private fun observeDetailArticle() {
        lifecycleScope.launchWhenStarted {
            detailNewsViewModel.detailArticle.collectLatest { article ->
                if (article != null) {
                    binding.tvTitle.text = article.title ?: "No Title"
                    binding.author.text = "Author: ${article.author ?: "Unknown"}"
                    binding.date.text = article.createdDate?.seconds?.let { formatDate(it) }
                        ?: "No Date"
                    binding.desc.text = article.description ?: "No Description"

                    Glide.with(this@DetailNewsActivity)
                        .load(article.image)
                        .placeholder(R.drawable.image)
                        .into(binding.img)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            detailNewsViewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launchWhenStarted {
            detailNewsViewModel.errorMessage.collectLatest { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(this@DetailNewsActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}