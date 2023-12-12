package com.example.shop

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.example.shop.databinding.CustomTextviewOneLineBinding

class OneLineTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defAttrsSet: Int = 0
) : LinearLayout(context, attributeSet, defAttrsSet) {
    private var binding: CustomTextviewOneLineBinding

    init {
        binding = CustomTextviewOneLineBinding.inflate(
            LayoutInflater.from(context),
            this
        )
        context.withStyledAttributes(
            attributeSet,
            R.styleable.OneLineTextView, defAttrsSet, 0
        ) {
            getString(R.styleable.OneLineTextView_description)?.let { description ->
                binding.textViewDescription.text = description
            }
            getString(R.styleable.OneLineTextView_data)?.let { data ->
                binding.textViewData.text = data
            }
        }
    }

    fun setDescription(text: String) {
        binding.textViewDescription.text = text
    }

    fun setData(text: String) {
        binding.textViewData.text = text
    }
}