package com.capstone.diabticapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Time {
    fun formatDate(seconds: Int?, nanoseconds: Int? = null): String {
        return if (seconds != null) {
            val date = Date(seconds * 1000L) // Convert seconds to milliseconds
            val dateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault())
            dateFormat.format(date)
        } else {
            "Unknown Date"
        }
    }

}