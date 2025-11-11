package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.souqcustomer.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RvCategoriesItemsBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.Categories2
import com.example.souqcustomer.pojo.Categories2Item

class CategoriesAdadpter(
    var categories2: ArrayList<Categories2Item>,
    var listener: OnClick
) : RecyclerView.Adapter<CategoriesAdadpter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(val binding: RvCategoriesItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesViewHolder {
        val binding =
            RvCategoriesItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoriesViewHolder,
        position: Int
    ) {

        val item=categories2[position]
        holder.binding.textCategory.text=item.name?: ""
        Glide.with(holder.itemView.context)
            .load(item.image?: "")
            .placeholder(R.drawable.category)
            .into(holder.binding.imgCategory)

        holder.itemView.setOnClickListener {
            val categoryId=item.id
            if (categoryId != null) {
                listener.OnClick(categoryId)
            }
        }

    }

    override fun getItemCount(): Int =  minOf(6, categories2.size)
}