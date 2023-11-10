package com.example.products.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.common.utils.data.remote.model.ApiCategories
import com.example.products.databinding.ItemCategoriesBinding
import javax.inject.Inject

class CategoriesAdapter @Inject constructor() :
    ListAdapter<ApiCategories, CategoriesAdapter.DataViewHolder>(
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

                buttonCategory.setOnClickListener {
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