package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RvCustomisedCategoryBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.Sellers
import com.example.souqcustomer.pojo.SellersItem

class CustomisedCategoryAdapter(
    val Sellers: ArrayList<SellersItem>,
    val listener: OnClick,
    private val onFavoriteClick: (seller: SellersItem, position: Int) -> Unit
) :
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



        holder.binding.btnFavourite.isSelected = item.isFavorite
        holder.binding.btnFavourite.setOnClickListener {
            item.isFavorite = !item.isFavorite
            holder.binding.btnFavourite.isSelected = item.isFavorite
            onFavoriteClick(item, position)
        }

        holder.itemView.setOnClickListener {
            listener.OnClick(item.user_id)
        }
    }

    override fun getItemCount(): Int = Sellers.size
}