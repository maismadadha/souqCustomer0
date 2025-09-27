package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvMyAddressesItemBinding
import com.example.souqcustomer.interface0.OnClick

class MyAddressesAdapter(val lisetener: OnClick): RecyclerView.Adapter<MyAddressesAdapter.ViewHolder>() {
        inner class ViewHolder(val binding: RvMyAddressesItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
       val binding = RvMyAddressesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener {
            lisetener.OnClick(position)
            holder.binding.selectedAddressCheckMark.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int =3
}