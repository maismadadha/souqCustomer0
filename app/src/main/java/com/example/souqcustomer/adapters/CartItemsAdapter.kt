package com.example.souqcustomer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.RvCartItemsBinding
import com.example.souqcustomer.interface0.CartItemListener
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.OrderItemDto

class CartItemsAdapter(
    private var items: List<OrderItemDto>,
    private val listener: CartItemListener
) :
    RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvCartItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvCartItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = items[position]


        holder.binding.itemName.text = item.product?.name ?: ""
        holder.binding.itemDescription.text = item.product?.description ?: ""
        holder.binding.itemPrice.text = item.price.toString()
        holder.binding.quantity.text = item.quantity.toString()

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
            .placeholder(R.drawable.category)
            .error(R.drawable.category)
            .centerCrop()
            .into(holder.binding.itemImage)
        Log.d("CartAdapter", "img = ${item.product?.main_image_url}")




        holder.binding.minusButton.setOnClickListener {
            val current = holder.binding.quantity.text.toString().toInt()
            if (current > 1) {
                holder.binding.quantity.text = (current - 1).toString()
            }
        }


        holder.itemView.setOnClickListener {
            listener.onItemClick(item.product_id)
        }

        holder.binding.plusButton.setOnClickListener {
            val newQty = item.quantity + 1
            listener.onIncreaseQuantity(item.copy(quantity = newQty))
        }

        holder.binding.minusButton.setOnClickListener {
            if (item.quantity > 1) {
                val newQty = item.quantity - 1
                listener.onDecreaseQuantity(item.copy(quantity = newQty))
            }
        }


    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<OrderItemDto>) {
        items = newItems
        notifyDataSetChanged()
    }
    fun getItemAt(position: Int): OrderItemDto = items[position]

}