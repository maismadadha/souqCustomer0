package com.example.souqcustomer.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.ProductImagesAdapter
import com.example.souqcustomer.databinding.ActivityProductBinding


class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        val adapter = ProductImagesAdapter()
        binding.rvProductImages.adapter = adapter
        binding.rvProductImages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvProductImages)
        binding.indicator.attachToRecyclerView(binding.rvProductImages, snapHelper)
        adapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        binding.btnAddToCart.setOnClickListener {
            finish()
        }

    }

}