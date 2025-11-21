package com.example.souqcustomer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RcOrderItemsBinding
import com.example.souqcustomer.pojo.OrderItemDto

class OrderDetailsItemsAdapter(val items: List<OrderItemDto>) : RecyclerView.Adapter<OrderDetailsItemsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RcOrderItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
      val  binding = RcOrderItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
      val  item=items[position]
        holder.binding.itemName.text = item.product?.name ?: ""
        holder.binding.itemDescription.text = item.product?.description ?: ""
        holder.binding.itemPrice.text = item.price.toString()
        holder.binding.itemCount.text = item.quantity.toString()

        val customizationText = item.customizations
            ?.entries
            ?.joinToString(" • ") { (key, value) -> "$key: $value" }
            ?: ""

        if (customizationText.isEmpty()) {
            holder.binding.itemCustomizations.visibility = View.GONE
        } else {
            holder.binding.itemCustomizations.visibility = View.VISIBLE
            holder.binding.itemCustomizations.text = customizationText
        }

        Glide.with(holder.itemView.context)
            .load(item.product?.main_image_url)
            .into(holder.binding.itemImage)
        Log.d("CartAdapter", "img = ${item.product?.main_image_url}")

    }

    override fun getItemCount(): Int =items.size
}