package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvCartItemsBinding
import com.example.souqcustomer.interface0.OnClick

class CartItemsAdapter(val listener: OnClick) :
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
        holder.binding.plusButton.setOnClickListener {
            holder.binding.quantity.text =
                (holder.binding.quantity.text.toString().toInt() + 1).toString()
        }

        holder.binding.minusButton.setOnClickListener {
            holder.binding.quantity.text =
                (holder.binding.quantity.text.toString().toInt() - 1).toString()
        }


        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }


    }

    override fun getItemCount(): Int = 10
}