package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.souqcustomer.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RvNewProductsItemsBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.AllProducts
import com.example.souqcustomer.pojo.AllProductsItem

class NewProductsAdapter(
    var newProducts: ArrayList<AllProductsItem>,
    var listener : OnClick
): RecyclerView.Adapter<NewProductsAdapter.ViewHolder>() {
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
        val item = newProducts[position]
        holder.binding.producteName.text=item.name?:""
        holder.binding.price.text=item.price?:""
        Glide.with(holder.itemView.context)
            .load(item.cover_image ?: "")
            .placeholder(R.drawable.category)
            .error(R.drawable.category)
            .centerCrop()
            .into(holder.binding.newProductCover)

        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }

       // holder.binding.newProductCover.setImageResource(R.drawable.img_product)
      //  holder.binding.producteName.text="عطر Vanilla"
       // holder.binding.price.text="15.00 JD"
    }

    override fun getItemCount(): Int =newProducts.size
}