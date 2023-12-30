package com.example.products.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.example.model.Product
import com.example.products.R
import com.example.products.databinding.ItemProductBinding
import javax.inject.Inject

class ProductsAdapter @Inject constructor() :
    ListAdapter<Product, ProductsAdapter.DataViewHolder>(
        diffUtilCallback
    ) {


    private var onItemClick: (Product) -> Unit = {}

    fun setItemCallback(callback: (Product) -> Unit) {
        this.onItemClick = callback
    }
    private var onClick: (ErrorResult) -> Unit = {}
    fun setCallback(callback: (ErrorResult) -> Unit) {
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
                    listener(onSuccess = { _, _ ->
                        placeholder(R.drawable.ic_placeholder_135)
                    }, onError = { _: ImageRequest, error ->
                        onClick.invoke(error)
                    })

                    error(com.example.data.R.drawable.ic_menu_24)
                }
                textViewNameProduct.text = item.nameProduct
                textViewInfoProduct.text = item.productCategory
                root.setOnClickListener{
                    onItemClick(item)
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