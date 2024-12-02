package com.capstone.diabticapp.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.capstone.diabticapp.R

class ProfileAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : Toolbar(context, attrs) {

    private var titleTextView: TextView
    private var saveButton: ImageView
    private var cancelButton: ImageView
    private var backButton: ImageView

    init {
        inflate(context, R.layout.custom_app_bar_profile, this)

        titleTextView = findViewById(R.id.toolbarTitle)
        saveButton = findViewById(R.id.toolbarSave)
        cancelButton = findViewById(R.id.toolbarCancel)
        backButton = findViewById(R.id.toolbarBack)

        saveButton.visibility = GONE
        cancelButton.visibility = GONE
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun showEditActions() {
        saveButton.visibility = VISIBLE
        cancelButton.visibility = VISIBLE
    }

    fun hideEditActions() {
        saveButton.visibility = GONE
        cancelButton.visibility = GONE
    }

    fun setSaveClickListener(listener: OnClickListener) {
        saveButton.setOnClickListener(listener)
    }

    fun setCancelClickListener(listener: OnClickListener) {
        cancelButton.setOnClickListener(listener)
    }

    fun setBackClickListener(listener: OnClickListener) {
        backButton.setOnClickListener(listener)
    }
}