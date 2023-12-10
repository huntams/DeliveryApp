package com.example.products.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.model.Product
import com.example.products.R
import com.example.products.databinding.ItemProductBinding
import javax.inject.Inject

class ProductsAdapter @Inject constructor() :
    ListAdapter<Product, ProductsAdapter.DataViewHolder>(
        diffUtilCallback
    ) {

    private var onClick: (Product) -> Unit = {}
    fun setCallback(callback: (Product) -> Unit) {
        this.onClick = callback
    }

    private var buttonClick: (Product) -> Unit = {}
    fun setButtonCallback(callback: (Product) -> Unit) {
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
        fun bind(item: Product) {

            with(binding) {
                buttonPrice.text = item.getFormattedPrice()
                imageViewProduct.load(item.image) {
                    error(R.drawable.ic_placeholder_135)
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_135)
                }
                textViewNameProduct.text = item.nameProduct
                textViewInfoProduct.text = item.productCategory
                root.setOnClickListener {
                    onClick.invoke(item)
                }
                buttonPrice.setOnClickListener {
                    item.productInCart += 1
                    buttonClick(item)
                }

            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<Product>() {

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.id == newItem.id
    }
}