package com.example.souqcustomer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.souqcustomer.fragments.ProductsByCategoryFragment
import com.example.souqcustomer.pojo.SellerCategories
import com.example.souqcustomer.pojo.SellerCategoriesItem

class ProductsByCategoryViewPagerAdapter(
    activity: FragmentActivity,
    private val categories: SellerCategories,
    private val sellerId: Int
) : FragmentStateAdapter(activity) {


    override fun createFragment(position: Int): Fragment {
        val cat = categories[position]
        return ProductsByCategoryFragment.newInstance(sellerId, cat.id)
    }

    override fun getItemCount(): Int = categories.size
}