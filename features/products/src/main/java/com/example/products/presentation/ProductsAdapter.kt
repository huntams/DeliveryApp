package com.example.products.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.network.model.ApiProductCategory
import com.example.products.R
import com.example.products.databinding.ItemProductBinding
import javax.inject.Inject

class ProductsAdapter @Inject constructor() :
    ListAdapter<ApiProductCategory, ProductsAdapter.DataViewHolder>(
        diffUtilCallback
    ) {

    private var onClick: (ApiProductCategory) -> Unit = {}
    fun setCallback(callback: (ApiProductCategory) -> Unit) {
        this.onClick = callback
    }

    private var buttonClick: (ApiProductCategory) -> Unit = {}
    fun setButtonCallback(callback: (ApiProductCategory) -> Unit) {
        this.buttonClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ItemProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ApiProductCategory) {

            with(binding) {
                buttonPrice.text = item.getFormattedPrice()
                imageViewProduct.load(item.strMealThumb) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_135)
                }
                textViewNameProduct.text = item.strMeal
                textViewInfoProduct.text = item.strMealThumb
                root.setOnClickListener {
                    onClick.invoke(item)
                }
                buttonPrice.setOnClickListener {
                    item.countItem = item.countItem + 1
                    buttonClick(item)
                }

            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<ApiProductCategory>() {

    override fun areContentsTheSame(
        oldItem: ApiProductCategory,
        newItem: ApiProductCategory
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: ApiProductCategory,
        newItem: ApiProductCategory
    ): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }
}