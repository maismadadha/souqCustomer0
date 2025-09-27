package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvCustomisedCategoryBinding
import com.example.souqcustomer.interface0.OnClick

class CustomisedCategoryAdapter(val listener: OnClick): RecyclerView.Adapter<CustomisedCategoryAdapter.ViewHolder>() {
inner class ViewHolder(val binding: RvCustomisedCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvCustomisedCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    override fun getItemCount(): Int =10
}