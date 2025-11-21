package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.databinding.RvMyAddressesItemBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.AddressDto

class MyAddressesAdapter(
    private var addresses: List<AddressDto>,
    val lisetener: OnClick): RecyclerView.Adapter<MyAddressesAdapter.ViewHolder>() {
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
        val address = addresses[position]
        holder.binding.addressName.text = address.address_name
        holder.binding.addressDescription.text = "${address.city_name}, ${address.street ?: ""}, ${address.building_number ?: ""}"


        holder.itemView.setOnClickListener {
            lisetener.OnClick(position)
        }
    }

    override fun getItemCount(): Int =addresses.size

    fun updateList(newAddresses: List<AddressDto>) {
        addresses = newAddresses
        notifyDataSetChanged()
    }
}