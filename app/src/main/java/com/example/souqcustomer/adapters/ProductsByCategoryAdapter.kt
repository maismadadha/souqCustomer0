package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.RvProductsByCategoryItemBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.Products
import com.example.souqcustomer.pojo.ProductsItem

class ProductsByCategoryAdapter(val products : ArrayList<ProductsItem>, val listener : OnClick): RecyclerView.Adapter<ProductsByCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvProductsByCategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RvProductsByCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item=products[position]
        holder.binding.productName.text=item.name
        val price=item.price
        holder.binding.productPrice.text="${price} د.أ "
        holder.binding.productDescription.text=item.description
        Glide.with(holder.itemView.context)
            .load(item.cover_image?:"")
            .placeholder(R.drawable.category)
            .error(R.drawable.category)
            .centerCrop()
            .into(holder.binding.productImg)

        holder.binding.btnAddToCart.setOnClickListener {
            listener.OnClick(item.id)
        }

        holder.itemView.setOnClickListener {
            listener.OnClick(item.id)
        }
    }

    override fun getItemCount(): Int =products.size
}