package com.capstone.diabticapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.remote.response.DataItem
import com.capstone.diabticapp.utils.Time


class NewsAdapter(
    private val articles: List<DataItem>,
    private val onItemClick: (DataItem) -> Unit
    ) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    class NewsViewHolder(view: View, private val onItemClick: (DataItem) -> Unit) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.tv_title)
        private val timeTextView: TextView = view.findViewById(R.id.tv_time)
        private val thumbnailImageView: ImageView = view.findViewById(R.id.img_thumbnail)

        fun bind(article: DataItem) {
            titleTextView.text = article.title ?: "No Title"

            val formattedDate = Time.formatDate(article.createdDate?.seconds)
            timeTextView.text = formattedDate

            Glide.with(itemView.context)
                .load(article.image)
                .placeholder(R.drawable.image)
                .into(thumbnailImageView)

            itemView.setOnClickListener { onItemClick(article) }
        }

    }
}
