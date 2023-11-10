package com.example.products.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.products.databinding.ItemBannerBinding
import javax.inject.Inject

class BannersAdapter @Inject constructor(private val dataset: List<String>) :
    RecyclerView.Adapter<BannersAdapter.DataViewHolder>() {

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
