package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RvFavouiteStoreItemBinding
import com.example.souqcustomer.R
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.FavoriteStores
import com.example.souqcustomer.pojo.Sellers

class FavouriteStoresAdapter(
    var seller: FavoriteStores,
    val listener: OnClick
) : RecyclerView.Adapter<FavouriteStoresAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvFavouiteStoreItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RvFavouiteStoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = seller[position]
        holder.binding.storeName.text = item.store_name
        holder.binding.storeCategory.text = item.main_category
        Glide.with(holder.itemView.context)
            .load(item.store_logo_url)
            .into(holder.binding.storeLogo)


        holder.binding.btnFavourite.isSelected = true
        holder.binding.btnFavourite.setOnClickListener {
            holder.binding.btnFavourite.isSelected = !holder.binding.btnFavourite.isSelected
        }

        holder.itemView.setOnClickListener {
            listener.OnClick(item.store_id)
        }

    }

    override fun getItemCount(): Int = seller.size

    fun submitList(newList: FavoriteStores) {
        seller = newList
        notifyDataSetChanged()
    }
}