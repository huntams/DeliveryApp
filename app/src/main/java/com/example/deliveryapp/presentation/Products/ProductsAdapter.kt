package com.example.deliveryapp.presentation.Products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.databinding.ItemCategoriesBinding
import javax.inject.Inject

class ProductsAdapter @Inject constructor() : ListAdapter<ApiCategories, ProductsAdapter.DataViewHolder>(
    diffUtilCallback
) {

    private var onClick: (ApiCategories) -> Unit = {}
    fun setCallback(callback: (ApiCategories) -> Unit) {
        this.onClick = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DataViewHolder(
        private val binding: ItemCategoriesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ApiCategories) {

            with(binding) {
                buttonCategory.text = item.strCategory

                root.setOnClickListener {
                    onClick.invoke(item)
                }


            }

        }
    }
}
private val diffUtilCallback = object : DiffUtil.ItemCallback<ApiCategories>() {

    override fun areContentsTheSame(oldItem: ApiCategories, newItem: ApiCategories): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ApiCategories, newItem: ApiCategories): Boolean {
        return oldItem.strCategory == newItem.strCategory
    }
}