package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvFavouiteStoreItemBinding
import com.example.souqcustomer.R
import com.example.souqcustomer.interface0.OnClick

class FavouriteStoresAdapter(val listener : OnClick): RecyclerView.Adapter<FavouriteStoresAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvFavouiteStoreItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val binding = RvFavouiteStoreItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.storeName.text="لونا باجز"
        holder.binding.storeLogo.setImageResource(R.drawable.img_store)
        holder.binding.storeCategory.text="صحة و جمال"
        holder.binding.btnFavourite.isSelected=true
        holder.binding.btnFavourite.setOnClickListener {
            holder.binding.btnFavourite.isSelected=!holder.binding.btnFavourite.isSelected
        }

        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }

    }

    override fun getItemCount(): Int =15
}