package com.example.products.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Categories
import com.example.products.databinding.ItemCategoriesBinding
import javax.inject.Inject

class CategoriesAdapter @Inject constructor() :
    ListAdapter<Categories, CategoriesAdapter.DataViewHolder>(
        diffUtilCallback
    ) {

    private var onClick: (Categories) -> Unit = {}
    fun setCallback(callback: (Categories) -> Unit) {
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
        fun bind(item: Categories) {

            with(binding) {
                buttonCategory.text = item.strCategory

                buttonCategory.setOnClickListener {
                    onClick.invoke(item)
                }
            }

        }
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<Categories>() {

    override fun areContentsTheSame(oldItem: Categories, newItem: Categories): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Categories, newItem: Categories): Boolean {
        return oldItem.strCategory == newItem.strCategory
    }
}