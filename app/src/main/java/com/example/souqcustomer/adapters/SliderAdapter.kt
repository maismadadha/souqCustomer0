package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.RvSliderItemsBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.SliderAdsItem

class SliderAdapter(
    var sliderAds : ArrayList<SliderAdsItem>,
    var listener: OnClick
): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    inner class SliderViewHolder(val binding: RvSliderItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
       val binding = RvSliderItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SliderViewHolder,
        position: Int
    ) {
        val item=sliderAds[position]
        holder.binding.titleSlider.text=item.title?:""
        holder.binding.descriptionSlider.text=item.description?:""
        Glide.with(holder.itemView.context)
            .load(item.image_url?: "")
            .placeholder(R.drawable.category)
            .into(holder.binding.imgSlider)

        holder.itemView.setOnClickListener {
            val sellerId = item.store_id
            if (sellerId != null) {
                listener.OnClick(sellerId)
            }
        }

    }

    override fun getItemCount(): Int =sliderAds.size
}