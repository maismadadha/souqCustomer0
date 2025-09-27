package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvSearchItemsBinding
import com.example.souqcustomer.interface0.OnClick

class SearchResultsAdapter(val listener : OnClick) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvSearchItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvSearchItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.btnFavourite.setOnClickListener {
            holder.binding.btnFavourite.isSelected = !holder.binding.btnFavourite.isSelected
        }
        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }
    }

    override fun getItemCount(): Int =15
}