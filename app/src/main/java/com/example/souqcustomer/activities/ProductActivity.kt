package com.example.souqcustomer.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.ProductImagesAdapter
import com.example.souqcustomer.adapters.ProductOptionsAdapter
import com.example.souqcustomer.databinding.ActivityProductBinding
import com.example.souqcustomer.pojo.ProductOptionsItem
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
        val optionsAdapter = ProductOptionsAdapter { _, _ ->
            updateAddButtonState()
        }
        binding.rvOptions.adapter = optionsAdapter
        binding.rvOptions.layoutManager = LinearLayoutManager(this)
        binding.rvOptions.itemAnimator = null

            productId = intent?.getIntExtra("productId", 0) ?: 0
            viewModel = ViewModelProvider(this)[SellerViewModel::class.java]

            viewModel.getProductImages(productId)
            observeProductImagesLiveData()

            viewModel.getProductById(productId)
            observeProductByIdLiveData()

        viewModel.getProductOptions(productId)
        observeProductOptionsLiveData(optionsAdapter)


        binding.back.setOnClickListener {
                finish()
            }

            binding.btnAddToCart.setOnClickListener {
                finish()
            }

        }
    private var screenOptions: List<ProductOptionsItem> = emptyList()

    private fun observeProductOptionsLiveData(optionsAdapter: ProductOptionsAdapter) {
        viewModel.getLiveProductOptions().observe(this) { options ->
            screenOptions = options ?: emptyList()
            optionsAdapter.submitList(screenOptions)
            updateAddButtonState()
        }
    }

    private fun observeProductByIdLiveData() {
        viewModel.getLiveProductById().observe(this) { product ->
            if (product == null) return@observe


            binding.productName.text        = product.name ?: ""
            binding.productName2.text=product.name?:""
            binding.productDescription.text = product.description ?: ""
            binding.productPrice.text       = product.price
        }
    }


    private fun observeProductImagesLiveData() {
        viewModel.getLiveProductImages().observe(this){images->
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

    private fun updateAddButtonState() {
        val adapter = binding.rvOptions.adapter as? ProductOptionsAdapter ?: return
        val sel = adapter.currentSelection() // Map<optionId, valueId>
        val allRequiredSelected = screenOptions.all { opt ->
            opt.required == 0 || sel[opt.id] != null
        }
        binding.btnAddToCart.isEnabled = allRequiredSelected
    }

}