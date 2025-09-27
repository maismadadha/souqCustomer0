package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.CartActivity
import com.example.souqcustomer.activities.CustomisedCategoryActivity
import com.example.souqcustomer.activities.SearchActivity
import com.example.souqcustomer.activities.StoreActivity
import com.example.souqcustomer.adapters.CategoriesAdadpter
import com.example.souqcustomer.adapters.NewProductsAdapter
import com.example.souqcustomer.adapters.SliderAdapter
import com.example.souqcustomer.adapters.SuggestedStoresAdapter
import com.example.souqcustomer.databinding.FragmentHomeBinding
import com.example.souqcustomer.interface0.OnClick


class homeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sliderAdapter = SliderAdapter()
        val categoriesAdapter = CategoriesAdadpter(object : OnClick{
            override fun OnClick(index: Int) {
                val intent = Intent(requireContext(), CustomisedCategoryActivity::class.java)
                startActivity(intent)
            }
        })
        val suggestedStoresAdapter = SuggestedStoresAdapter(object : OnClick{
            override fun OnClick(index: Int) {
                val intent = Intent(requireContext(), StoreActivity::class.java)
                startActivity(intent)
            }
        })
        val newProductsAdapter= NewProductsAdapter()


        binding.rvSlider.adapter = sliderAdapter
        binding.rvSlider.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)

        binding.rvSuggestedStores.layoutDirection = View.LAYOUT_DIRECTION_RTL
        binding.rvSuggestedStores.adapter = suggestedStoresAdapter
        binding.rvSuggestedStores.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvNewProducts.layoutDirection=View.LAYOUT_DIRECTION_RTL
        binding.rvNewProducts.adapter = newProductsAdapter
        binding.rvNewProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvSlider)

        binding.indicator.attachToRecyclerView(binding.rvSlider, snapHelper)
        sliderAdapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        binding.cart.setOnClickListener {

            val intent = Intent(requireContext(), CartActivity::class.java)
            startActivity(intent)
        }

        binding.searchBar.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }




    }


}