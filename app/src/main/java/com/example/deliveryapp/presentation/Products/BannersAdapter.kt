package com.example.deliveryapp.presentation.Products

import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.deliveryapp.data.remote.model.ApiCategories
import com.example.deliveryapp.databinding.ItemBannerBinding
import com.example.deliveryapp.databinding.ItemCategoriesBinding
import javax.inject.Inject

class BannersAdapter @Inject constructor(private val dataset: List<String>) : RecyclerView.Adapter<BannersAdapter.DataViewHolder>() {

    private var onClick: (List<String>) -> Unit = {}
    fun setCallback(callback: (List<String>) -> Unit) {
        this.onClick = callback
    }
    inner class DataViewHolder(
        private val binding: ItemBannerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
    }
    override fun onBindViewHolder(viewHolder: DataViewHolder, position: Int) {
    }
    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }


}
