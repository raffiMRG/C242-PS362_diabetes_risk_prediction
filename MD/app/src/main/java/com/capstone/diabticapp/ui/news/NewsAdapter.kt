package com.capstone.diabticapp.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ItemNewsBinding
import com.capstone.diabticapp.ui.detail_news.DetailNewsActivity
import com.capstone.diabticapp.ui.home.HomeAdapter
import com.google.android.material.imageview.ShapeableImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(
    private val items: List<ExampleNews>
): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val thumbnail: ShapeableImageView = itemView.findViewById(R.id.img_thumbnail)
        private val title: TextView = itemView.findViewById(R.id.tv_title)

        fun bind(item: ExampleNews){
            Glide.with(itemView)
                .load(item.image)
                .into(thumbnail)
            title.text = item.title
            itemView.setOnClickListener{
                val intentDetail = Intent(this.itemView.context, DetailNewsActivity::class.java)
                intentDetail.putExtra("image", item.image)
                intentDetail.putExtra("title", item.title)
                intentDetail.putExtra("description", item.description)
                this.itemView.context.startActivity(intentDetail)
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_card, parent, false)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

}