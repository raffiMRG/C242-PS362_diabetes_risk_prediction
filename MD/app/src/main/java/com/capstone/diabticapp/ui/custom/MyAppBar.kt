package com.capstone.diabticapp.ui.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.capstone.diabticapp.R

class MyAppBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
//        setPadding(40, 24, 20, 24) // Padding di sekitar toolbar

        // Menambahkan tombol kembali
        val backButton = ImageView(context).apply {
            id = generateViewId()
            setImageResource(R.drawable.baseline_arrow_back_ios_24)  // Ganti dengan ikon yang diinginkan
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                // Menambahkan constraint
                topToTop = LayoutParams.PARENT_ID
                startToStart = LayoutParams.PARENT_ID
                setMargins(0, 20, 0, 0) // Margin atas
            }
            setOnClickListener { (context as? AppCompatActivity)?.onBackPressed() }
        }

        // Menambahkan judul
        val titleText = TextView(context).apply {
            id = generateViewId()
            text = "Default Title"
            textSize = 24f
//            typeface = Typeface.DEFAULT_BOLD
            typeface = ResourcesCompat.getFont(context, R.font.raleway_bold)
            setTextColor(ContextCompat.getColor(context, R.color.purple))
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                // Menambahkan constraint
                topToTop = backButton.id  // Menggunakan ID dari backButton
                bottomToBottom = backButton.id
                startToEnd = backButton.id
                setMargins(16, 0, 0, 0)
            }
        }

        // Menambahkan elemen-elemen ke dalam toolbar
        addView(backButton)
        addView(titleText)

        // Memungkinkan untuk menggunakan atribut XML (jika diperlukan)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomToolbar)
            val title = typedArray.getString(R.styleable.CustomToolbar_toolbarTitle) ?: "Default Title"
            titleText.text = title
            typedArray.recycle()
        }
    }
}