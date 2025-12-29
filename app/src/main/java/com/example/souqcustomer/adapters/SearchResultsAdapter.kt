package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.RvSearchItemsBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.SellersItem

class SearchResultsAdapter(
    private val listener: OnClick
) : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    private val items = mutableListOf<SellersItem>()

    inner class ViewHolder(val binding: RvSearchItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvSearchItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val seller = items[position]

        // اسم المتجر
        holder.binding.storeName.text = seller.name
        holder.binding.storeCategory.text=seller.main_category.name
        Glide.with(holder.itemView.context)
            .load(seller.store_logo_url?:"")
            .placeholder(R.drawable.category)
            .error(R.drawable.category)
            .centerCrop()
            .into(holder.binding.storeLogo)

        // زر المفضلة (UI فقط)
        holder.binding.btnFavourite.setOnClickListener {
            it.isSelected = !it.isSelected
        }

        // الضغط على المتجر
        holder.itemView.setOnClickListener {
            listener.OnClick(seller.user_id)
        }
    }

    override fun getItemCount(): Int = items.size

    // 👈 هاي أهم دالة
    fun submitList(list: List<SellersItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    // عشان نجيب العنصر بالـ Activity
    fun getItem(position: Int): SellersItem = items[position]
}
