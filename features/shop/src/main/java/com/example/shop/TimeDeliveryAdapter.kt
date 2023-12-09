package com.example.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shop.databinding.ItemTimeDeliveryBinding
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class TimeDeliveryAdapter @Inject constructor() :
    ListAdapter<TimeData, TimeDeliveryAdapter.DataViewHolder>(diffUtilCallback) {


    private var buttonClick: (TimeData) -> Unit = {}
    fun setButtonCallback(callback: (TimeData) -> Unit) {
        this.buttonClick = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ItemTimeDeliveryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    inner class DataViewHolder(
        private val binding: ItemTimeDeliveryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TimeData) {
            binding.buttonTime.text = convertLongToTime(item.time)
            binding.buttonTime.setOnClickListener {
                buttonClick(item)
            }
        }

    }

    override fun onBindViewHolder(viewHolder: DataViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<TimeData>() {

    override fun areContentsTheSame(
        oldItem: TimeData,
        newItem: TimeData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(
        oldItem: TimeData,
        newItem: TimeData
    ): Boolean {
        return oldItem.id == newItem.id
    }
}
