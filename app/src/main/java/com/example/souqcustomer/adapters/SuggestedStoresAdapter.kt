package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvSuggestedStoresItemsBinding
import com.example.souqcustomer.R
import com.example.souqcustomer.interface0.OnClick

class SuggestedStoresAdapter(val listener : OnClick): RecyclerView.Adapter<SuggestedStoresAdapter.SuggestedStoresViewHolder>() {
    inner class SuggestedStoresViewHolder(val binding: RvSuggestedStoresItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SuggestedStoresViewHolder {
        val binding = RvSuggestedStoresItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SuggestedStoresViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SuggestedStoresViewHolder,
        position: Int
    ) {
       holder.binding.storeCover.setImageResource(R.drawable.sliderpic)
        holder.binding.storeName.text="ميلا شوب"

        holder.binding.btnFavourite.isSelected=false
        holder.binding.btnFavourite.setOnClickListener {
            holder.binding.btnFavourite.isSelected=!holder.binding.btnFavourite.isSelected
        }

        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }
    }

    override fun getItemCount(): Int =5
}