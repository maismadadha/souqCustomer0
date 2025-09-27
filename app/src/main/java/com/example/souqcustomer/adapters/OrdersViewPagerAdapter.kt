package com.example.souqcustomer.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.souqcustomer.fragments.CurrentOrders
import com.example.souqcustomer.fragments.PreviousOrdersFragment

class OrdersViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CurrentOrders()
            1 -> PreviousOrdersFragment()
            else -> CurrentOrders()
        }
    }

    override fun getItemCount(): Int =2
}