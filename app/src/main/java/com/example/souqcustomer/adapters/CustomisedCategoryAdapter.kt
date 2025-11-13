package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RvCustomisedCategoryBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.Sellers

class CustomisedCategoryAdapter(val Sellers: Sellers, val listener: OnClick) :
    RecyclerView.Adapter<CustomisedCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvCustomisedCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RvCustomisedCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = Sellers[position]
        holder.binding.storeName.text = item.name
        holder.binding.storeCategory.text = item.main_category.name
        Glide.with(holder.itemView.context)
            .load(item.store_logo_url)
            .into(holder.binding.storeLogo)



        holder.binding.btnFavourite.setOnClickListener {
            holder.binding.btnFavourite.isSelected = !holder.binding.btnFavourite.isSelected
        }

        holder.itemView.setOnClickListener {
            listener.OnClick(item.user_id)
        }
    }

    override fun getItemCount(): Int = Sellers.size
}