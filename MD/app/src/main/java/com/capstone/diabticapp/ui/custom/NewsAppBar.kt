package com.capstone.diabticapp.ui.custom

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.capstone.diabticapp.R

@Suppress("DEPRECATION")
class NewsAppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val backButton: ImageView
    private val titleTextView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_news_app_bar, this, true)

        backButton = findViewById(R.id.back_button)
        titleTextView = findViewById(R.id.title)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.NewsAppBar)
        titleTextView.text = attributes.getString(R.styleable.NewsAppBar_title)
        attributes.recycle()

        backButton.setOnClickListener {
            val activity = context as? Activity
            activity?.onBackPressed()
        }
    }

    fun setAppBarTitle(title: String) {
        titleTextView.text = title
    }
}
