package com.example.souqcustomer.activities

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.ProductsByCategoryViewPagerAdapter
import com.example.souqcustomer.databinding.ActivityStoreBinding
import com.example.souqcustomer.viewModel.SellerViewModel
import com.google.android.material.tabs.TabLayoutMediator

class StoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding

    private lateinit var viewModel: SellerViewModel
    private var sellerId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sellerId = intent?.getIntExtra("sellerId", 0) ?: 0


        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]

        viewModel.getSellerById(sellerId)
        observeSellerByIdLiveData()

        viewModel.getSellerCategories(sellerId)
        observeSellerCategoriesLiveData()

        //favourite
        binding.btnFavourite.setOnClickListener {
            binding.btnFavourite.isSelected = !binding.btnFavourite.isSelected
        }

        //back
        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun observeSellerCategoriesLiveData() {
        viewModel.getLiveSellerCategories().observe(this) { categories ->
            if (categories.isNullOrEmpty()) return@observe
            val pagerAdapter = ProductsByCategoryViewPagerAdapter(
                this,
                categories,     // ✅ اللستة اللي إجت من السيرفر
                sellerId
            )
            binding.viewPager.adapter = pagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = categories[position].name
            }.attach()

            val tabLayout = binding.tabLayout
            for (i in 0 until tabLayout.tabCount) {
                val tabs = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
                val layoutParams = tabs.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(16, 0, 16, 0) // مسافة يمين ويسار لكل تاب
                tabs.layoutParams = layoutParams
            }
        }
    }

    private fun observeSellerByIdLiveData() {
        viewModel.getLiveSellerById().observe(this) { seller ->
            if (seller != null) {
                binding.storeName.text = seller.name ?: ""
                binding.storeNameCard.text = seller.name ?: ""
                binding.storeCategoryCard.text = seller.main_category.name ?: ""
                binding.storeDescriptionCard.text = seller.store_description ?: ""

                //delivery_price
                var price = ""
                var delivery_price = seller.delivery_price
                if (delivery_price == null)
                    price = "3.0 د.أ"
                else
                    price = " د.أ $delivery_price"
                binding.deliveryPrice.text = price

                //time
                var time = ""
                var timeDays = seller.preparation_days
                var timeHours = seller.preparation_hours
                timeHours?.let {
                    if (it >= 24) {
                        timeDays = timeDays?.plus(timeHours / 24 + 1)
                        timeHours = 0
                    }
                }
                if (timeDays == 0 && timeHours == 0)
                    time = " 2-3 ساعة"
                else if (timeDays == 0)
                    time = " ساعة $timeHours"
                else
                    time = " يوم $timeDays"
                binding.tvDeliveryTime.text = time

                Glide.with(this)
                    .load(seller.store_logo_url)
                    .into(binding.storeLogo)
                Glide.with(this)
                    .load(seller.store_cover_url)
                    .into(binding.storeCover)

            }


        }
    }

}
