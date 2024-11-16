package com.capstone.diabticapp.ui.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.diabticapp.R

class SettingAdapter(
    private val items: List<SettingItem>,
    private val listener: (SettingItem) -> Unit
) : RecyclerView.Adapter<SettingAdapter.SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
        return SettingViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class SettingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.ivIcon)
        private val title: TextView = itemView.findViewById(R.id.tvTitle)

        fun bind(item: SettingItem) {
            icon.setImageResource(item.iconRes)
            title.text = item.title
            itemView.setOnClickListener { listener(item) }
        }
    }
}
