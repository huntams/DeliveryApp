package com.example.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.model.Product
import com.example.shop.databinding.ItemAddProductBinding
import javax.inject.Inject

class AddProductAdapter @Inject constructor() :
    ListAdapter<Product, AddProductAdapter.DataViewHolder>(
        diffUtilCallback
    ) {

    private var onButtonClick: (Product) -> Unit = {}
    fun setButtonCallback(callback: (Product) -> Unit) {
        this.onButtonClick = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemAddProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ItemAddProductBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {

            with(binding) {
                textViewProduct.text = item.nameProduct
                buttonPrice.text = item.getFormattedPrice()
                imageViewProduct.load(item.image)
                buttonPrice.setOnClickListener {
                    onButtonClick.invoke(item)
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