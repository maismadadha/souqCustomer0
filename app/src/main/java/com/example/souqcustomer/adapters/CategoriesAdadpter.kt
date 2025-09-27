package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.souqcustomer.R
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvCategoriesItemsBinding
import com.example.souqcustomer.interface0.OnClick

class CategoriesAdadpter( var listener: OnClick): RecyclerView.Adapter<CategoriesAdadpter.CategoriesViewHolder>() {
    inner class CategoriesViewHolder(val binding: RvCategoriesItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesViewHolder {
        val binding = RvCategoriesItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoriesViewHolder,
        position: Int
    ) {
        holder.binding.imgCategory.setImageResource(R.drawable.category)
        holder.itemView.setOnClickListener {
            listener.OnClick(position)
        }
    }

    override fun getItemCount(): Int =6
}