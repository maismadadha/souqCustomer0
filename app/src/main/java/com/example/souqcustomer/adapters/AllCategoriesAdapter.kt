package com.example.souqcustomer.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvCategoriesItemsBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.Categories2Item
import android.view.LayoutInflater
import com.example.souqcustomer.R
import com.bumptech.glide.Glide


class AllCategoriesAdapter(
    private val categories2: ArrayList<Categories2Item>,
    private val listener: OnClick
) : RecyclerView.Adapter<AllCategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(val binding: RvCategoriesItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding = RvCategoriesItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = categories2[position]

        holder.binding.textCategory.text = item.name ?: ""

        Glide.with(holder.itemView.context)
            .load(item.image ?: "")
            .placeholder(R.drawable.category)
            .error(R.drawable.category)
            .into(holder.binding.imgCategory)

        holder.itemView.setOnClickListener {
            listener.OnClick(item.id)
        }
    }

    override fun getItemCount(): Int = categories2.size
}
