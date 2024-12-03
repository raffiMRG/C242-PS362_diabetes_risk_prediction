package com.capstone.diabticapp.ui.custom

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        isFocusable = true
        isClickable = true
        isFocusableInTouchMode = true

        doOnTextChanged { text, _, _, _ ->
            if (text != null && text.isNotEmpty() && !isValidEmail(text.toString())) {
                error = "Please enter a valid email address"
            }else{
                (parent.parent as? TextInputLayout)?.error = null
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}