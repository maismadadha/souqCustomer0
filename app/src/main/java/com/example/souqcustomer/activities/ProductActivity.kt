package com.example.souqcustomer.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.ProductImagesAdapter
import com.example.souqcustomer.databinding.ActivityProductBinding
import com.example.souqcustomer.viewModel.SellerViewModel


class ProductActivity : AppCompatActivity() {
    private var productId: Int = 0
    private lateinit var binding: ActivityProductBinding
    private lateinit var viewModel: SellerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productId=intent?.getIntExtra("productId", 0) ?: 0
        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]

        viewModel.getProductImages(productId)
        observeProductImagesLiveData()

        viewModel.getProductById(productId)
        observeProductByIdLiveData()

        binding.back.setOnClickListener {
            finish()
        }

        binding.btnAddToCart.setOnClickListener {
            finish()
        }

    }

    private fun observeProductByIdLiveData() {
        viewModel.getLiveProductById().observe(this) { product ->
            if (product == null) return@observe

            binding.productName.text        = product.name ?: ""
            binding.productDescription.text = product.description ?: ""
            binding.productPrice.text       = product.price?:""
        }
    }


    private fun observeProductImagesLiveData() {
        viewModel.getLivePriductImages().observe(this){images->
            val adapter= ProductImagesAdapter(images)
            binding.rvProductImages.adapter = adapter
            binding.rvProductImages.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvProductImages)
            binding.indicator.attachToRecyclerView(binding.rvProductImages, snapHelper)
            adapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)
        }
    }

}