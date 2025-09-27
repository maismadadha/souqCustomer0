package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.souqcustomer.R
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvCurrentOrdersItemBinding

class CurrentOrdersAdapter(): RecyclerView.Adapter<CurrentOrdersAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvCurrentOrdersItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvCurrentOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.storeImg.setImageResource(R.drawable.img_store)
        holder.binding.storeName.text="لونا باجز"
        holder.binding.orderPrice.text="100.00 د.أ"
        holder.binding.orderDate.text="21 يوليو"

    }

    override fun getItemCount(): Int =10
}