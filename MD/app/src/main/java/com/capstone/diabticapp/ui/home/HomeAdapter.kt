package com.capstone.diabticapp.ui.home

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.capstone.diabticapp.R
import java.security.PrivateKey

class HomeAdapter(
    private val items: List<HomeCardItem>,
    private val listener: (HomeCardItem) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val item_card: View = itemView.findViewById(R.id.home_item_card)

        fun bind(item: HomeCardItem, backgroundRes: Int) {
            icon.setImageResource(item.iconRes)
            title.text = item.title
            itemView.setOnClickListener { listener(item) }
            item_card.setBackgroundResource(backgroundRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_card, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val context = holder.itemView.context
        val uiMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        val backgroundRes = if (uiMode == Configuration.UI_MODE_NIGHT_YES) {
            R.drawable.card1_5night
        } else {
            R.drawable.card1_4
        }

        holder.bind(items[position], backgroundRes)
    }

    override fun getItemCount(): Int = items.size
}
