package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.souqcustomer.R
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvNewProductsItemsBinding

class NewProductsAdapter(): RecyclerView.Adapter<NewProductsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvNewProductsItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvNewProductsItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.binding.newProductCover.setImageResource(R.drawable.img_product)
        holder.binding.producteName.text="عطر Vanilla"
        holder.binding.price.text="15.00 JD"
    }

    override fun getItemCount(): Int =5
}