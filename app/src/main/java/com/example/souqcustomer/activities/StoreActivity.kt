package com.example.souqcustomer.activities

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.ProductsByCategoryViewPagerAdapter
import com.example.souqcustomer.databinding.ActivityStoreBinding
import com.google.android.material.tabs.TabLayoutMediator

class StoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFavourite.setOnClickListener {
            binding.btnFavourite.isSelected=!binding.btnFavourite.isSelected
        }

        binding.back.setOnClickListener {
            finish()
        }

        val adapter = ProductsByCategoryViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        val tabTitles = arrayOf("احذية", "ملابس", "عطور","oiuytreeeeeeeeeeeeeeeeeeeeeeee")

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
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
