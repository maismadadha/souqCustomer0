package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvProductsByCategoryItemBinding
import com.example.souqcustomer.interface0.OnClick

class ProductsByCategoryAdapter(val listener : OnClick): RecyclerView.Adapter<ProductsByCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvProductsByCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvProductsByCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }
    }

    override fun getItemCount(): Int =10
}