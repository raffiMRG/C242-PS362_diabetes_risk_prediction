package com.capstone.diabeticapp.ui.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.diabticapp.R
import com.capstone.diabticapp.ui.setting.SettingItem

class SettingAdapter(
    private val items: List<SettingItem>,
    private val listener: (SettingItem) -> Unit
) : RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
        return SettingViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    // Corrected parameter type to SettingViewHolder
    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(items[position]) // Call the bind function correctly
    }

    // Inner ViewHolder class
    inner class SettingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val title: TextView = itemView.findViewById(R.id.tvTitle)

        // Bind data to views
        fun bind(item: SettingItem) {
            icon.setImageResource(item.iconRes)
            title.text = item.title
            itemView.setOnClickListener { listener(item) }
        }
    }
}
