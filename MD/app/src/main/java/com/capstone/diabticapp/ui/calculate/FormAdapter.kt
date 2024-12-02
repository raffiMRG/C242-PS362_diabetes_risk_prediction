package com.capstone.diabticapp.ui.calculate

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.diabticapp.R
import com.capstone.diabticapp.databinding.ItemImageViewBinding
import com.capstone.diabticapp.databinding.ItemRadioButtonBinding
import com.capstone.diabticapp.databinding.ItemSliderBinding
import com.capstone.diabticapp.databinding.ItemTextInputBinding

class FormAdapter(
    private val listener: OnFormItemChangedListener
) : ListAdapter<FormItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FormItem>() {
            override fun areItemsTheSame(oldItem: FormItem, newItem: FormItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: FormItem, newItem: FormItem): Boolean =
                oldItem == newItem
        }
    }

    // Listener untuk menangani perubahan input
    interface OnFormItemChangedListener {
        fun onTextChanged(item: FormItem.TextInput)
        fun onOptionSelected(item: FormItem.RadioButtonInput)
        fun onSliderChanged(item: FormItem.SliderInput)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FormItem.TextInput -> R.layout.item_text_input
            is FormItem.RadioButtonInput -> R.layout.item_radio_button
            is FormItem.SliderInput -> R.layout.item_slider
            is FormItem.ImageItem -> R.layout.item_image_view
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_text_input -> TextInputViewHolder(ItemTextInputBinding.inflate(inflater, parent, false), listener)
            R.layout.item_radio_button -> RadioButtonViewHolder(ItemRadioButtonBinding.inflate(inflater, parent, false), listener)
            R.layout.item_slider -> SliderViewHolder(ItemSliderBinding.inflate(inflater, parent, false), listener)
            R.layout.item_image_view -> ImageViewHolder(ItemImageViewBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FormItem.TextInput -> (holder as TextInputViewHolder).bind(item)
            is FormItem.RadioButtonInput -> (holder as RadioButtonViewHolder).bind(item)
            is FormItem.SliderInput -> (holder as SliderViewHolder).bind(item)
            is FormItem.ImageItem -> (holder as ImageViewHolder).bind(item)
        }
    }

    class TextInputViewHolder(
        private val binding: ItemTextInputBinding,
        private val listener: OnFormItemChangedListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormItem.TextInput) {
            binding.textLabel.text = item.label
            binding.editText.hint = item.hint

            binding.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(editable: Editable?) {
                    item.text = if (editable.toString() == "") "0" else editable.toString()
                    listener.onTextChanged(item)
                }
            })
        }
    }

    class RadioButtonViewHolder(
        private val binding: ItemRadioButtonBinding,
        private val listener: OnFormItemChangedListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormItem.RadioButtonInput) {
            binding.label.text = item.label
            binding.radioGroup.removeAllViews()
            item.options.forEach { option ->
                val radioButton = RadioButton(binding.root.context).apply { text = option }
                binding.radioGroup.addView(radioButton)
            }

            binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                val selectedRadioButton = binding.radioGroup.findViewById<RadioButton>(checkedId)
                item.selectedOption = selectedRadioButton?.text.toString()
                listener.onOptionSelected(item)
            }
        }
    }

    class SliderViewHolder(
        private val binding: ItemSliderBinding,
        private val listener: OnFormItemChangedListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormItem.SliderInput) {
            binding.label.text = item.label
            binding.slider.valueFrom = item.valueFrom
            binding.slider.valueTo = item.valueTo
            binding.slider.stepSize = item.stepSize

            binding.slider.addOnChangeListener { _, value, _ ->
                item.currentValue = value
                listener.onSliderChanged(item)
            }
        }
    }

    class ImageViewHolder(
        private val binding: ItemImageViewBinding
    ) :RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormItem.ImageItem) {
            binding.imageView.apply {
                Glide.with(context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.tensi_meter)
                    .error(R.drawable.tensi_meter)
                    .into(binding.imageView)
            }
        }
    }
}
