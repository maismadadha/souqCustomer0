package com.example.souqcustomer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.souqcustomer.fragments.ProductsByCategoryFragment

class ProductsByCategoryViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return ProductsByCategoryFragment()
    }

    override fun getItemCount(): Int =4
}