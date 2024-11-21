package com.capstone.diabticapp.ui.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.diabticapp.R

class AccountProfileAdapter(
    private val fields: List<AccountItem>,
    private val onEditClick: (AccountItem) -> Unit
) : RecyclerView.Adapter<AccountProfileAdapter.AccountProfileViewHolder>() {

    class AccountProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val label: TextView = itemView.findViewById(R.id.tvLabel)
         val value: TextView = itemView.findViewById(R.id.tvValue)
         val editButton: ImageView = itemView.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountProfileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_profile_field, parent, false)
        return AccountProfileViewHolder(view)

    }

    override fun getItemCount(): Int = fields.size


    override fun onBindViewHolder(holder: AccountProfileViewHolder, position: Int) {
        val field = fields[position]
        holder.label.text = field.label
        holder.value.text = field.value
        holder.editButton.setOnClickListener { onEditClick(field) }

    }
}