package com.capstone.diabticapp.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.diabticapp.R
import com.capstone.diabticapp.data.database.UserEntity
import com.capstone.diabticapp.helper.DateConverterUtils
import com.google.android.material.imageview.ShapeableImageView

class HistoryAdapter(
    private val items: List<UserEntity>
): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.tv_date)
        private val status: TextView = itemView.findViewById(R.id.tv_result)

        fun bind(item: UserEntity) {
            val devaultTime = item.createdAt
            val wibTime = DateConverterUtils.convertUtcToWib(devaultTime)

            date.text = wibTime
            status.text = item.classification
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int = items.size
}