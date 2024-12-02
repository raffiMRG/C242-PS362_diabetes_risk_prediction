package com.capstone.diabticapp.ui.calculate

sealed class FormItem {
    data class TextInput(val id: String, val label: String, val hint: String, var text: String = "") : FormItem()
    data class RadioButtonInput(val id: String, val label: String, val options: List<String>, var selectedOption: String? = null) : FormItem()
    data class SliderInput(val id: String, val label: String, val valueFrom: Float, val valueTo: Float, val stepSize: Float, var currentValue: Float = 0f) : FormItem()
    data class ImageItem(val id: String, val imageUrl: String, val title: String) : FormItem()
}