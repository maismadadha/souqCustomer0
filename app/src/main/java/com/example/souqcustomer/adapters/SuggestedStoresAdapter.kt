package com.example.souqcustomer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RvSuggestedStoresItemsBinding
import com.example.souqcustomer.R
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.SellersItem

class SuggestedStoresAdapter(
    var sellers: ArrayList<SellersItem>,
    var listener: OnClick,
    private val onFavoriteClick: (seller: SellersItem, position: Int) -> Unit
) : RecyclerView.Adapter<SuggestedStoresAdapter.SuggestedStoresViewHolder>() {
    inner class SuggestedStoresViewHolder(val binding: RvSuggestedStoresItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuggestedStoresViewHolder {
        val binding = RvSuggestedStoresItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        Log.d("Sellers", "onBindViewHolder: $sellers")

        return SuggestedStoresViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SuggestedStoresViewHolder,
        position: Int
    ) {
        Log.d("Sellers", "onBindViewHolder: $sellers")
        val item = sellers[position]
        holder.binding.storeName.text = item.name ?: ""
        Glide.with(holder.itemView.context)
            .load(item.store_cover_url ?: "")
            .placeholder(R.drawable.category)
            .error(R.drawable.category)
            .centerCrop()
            .into(holder.binding.storeCover)




        holder.binding.btnFavourite.isSelected = item.isFavorite
        holder.binding.btnFavourite.setOnClickListener {
            item.isFavorite = !item.isFavorite
            holder.binding.btnFavourite.isSelected = item.isFavorite
            onFavoriteClick(item, position)
        }



        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }
    }

    override fun getItemCount(): Int = sellers.size
}