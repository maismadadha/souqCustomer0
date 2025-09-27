package com.example.souqcustomer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.OrdersViewPagerAdapter
import com.example.souqcustomer.databinding.FragmentOrderBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class orderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OrdersViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        val tabTitles = arrayOf("الحالية", "السابقة")


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabView = LayoutInflater.from(requireContext())
                .inflate(R.layout.tab_item, null) as TextView
            tabView.text = tabTitles[position]
            tab.customView = tabView
        }.attach()

        binding.tabLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
        binding.viewPager.layoutDirection = View.LAYOUT_DIRECTION_RTL


        val tab1 = binding.tabLayout.getTabAt(1)
        val tv1 = tab1?.customView as? TextView
        tv1?.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_hint_text))

        val tab0 = binding.tabLayout.getTabAt(0)
        val tv0 = tab0?.customView as? TextView
        tv0?.setTextColor(ContextCompat.getColor(requireContext(), R.color.old_mauve))



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tv = tab.customView as TextView
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.old_mauve))
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tv = tab.customView as TextView
                tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_hint_text))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.tabLayout.getTabAt(0)?.select()

    }
}
