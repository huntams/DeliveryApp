package com.example.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.model.ProductQuantityAndProduct
import com.example.shop.databinding.ItemProductQuantityBinding
import javax.inject.Inject

class OrderAdapter @Inject constructor() :
    ListAdapter<ProductQuantityAndProduct, OrderAdapter.DataViewHolder>(
        diffUtilCallback
    ) {

    private var onMinusClick: (ProductQuantityAndProduct) -> Unit = {}
    fun setCallbackMinusButton(callback: (ProductQuantityAndProduct) -> Unit) {
        this.onMinusClick = callback
    }

    private var onPlusClick: (ProductQuantityAndProduct) -> Unit = {}
    fun setCallbackPlusButton(callback: (ProductQuantityAndProduct) -> Unit) {
        this.onPlusClick = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemProductQuantityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ItemProductQuantityBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductQuantityAndProduct) {

            with(binding) {
                buttonCount.text = item.productQuantity.quantity.toString()
                val textProductPrice =item.product.getFormattedPrice()
                textViewProductPrice.text = textProductPrice
                imageViewProduct.load(item.product.image)
                textViewNameProduct.text = item.product.nameProduct
                textViewInfoProduct.text = item.product.productCategory

                buttonMinus.setOnClickListener {
                    onMinusClick.invoke(item)
                }
                buttonPlus.setOnClickListener {
                    //buttonCount.text = "${buttonCount.text.toString().toInt() + 1}"
                    onPlusClick.invoke(item)
                }
            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<ProductQuantityAndProduct>() {

    override fun areContentsTheSame(
        oldItem: ProductQuantityAndProduct,
        newItem: ProductQuantityAndProduct
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: ProductQuantityAndProduct,
        newItem: ProductQuantityAndProduct
    ): Boolean {
        return oldItem.productQuantity.id == newItem.productQuantity.id
    }
}