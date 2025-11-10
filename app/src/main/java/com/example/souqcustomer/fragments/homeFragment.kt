package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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
import com.example.souqcustomer.pojo.Categories2Item
import com.example.souqcustomer.viewModel.UserViewModel
import androidx.lifecycle.Observer


class homeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var categoriesAdapter : CategoriesAdadpter
    private lateinit var sliderAdsAdapter : SliderAdapter
    private lateinit var sellersAdapter: SuggestedStoresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sliderAds
        viewModel.getSliderAds()
        observeSliderAds()

        //Categories
        viewModel.getCategories2()
        observeCategories2()

        //sellers
        viewModel.getSellers()
        observeSellers()



        //new products
        val newProductsAdapter = NewProductsAdapter()
        binding.rvNewProducts.layoutDirection = View.LAYOUT_DIRECTION_RTL
        binding.rvNewProducts.adapter = newProductsAdapter
        binding.rvNewProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)




        //cart on click
        binding.cart.setOnClickListener {
            val intent = Intent(requireContext(), CartActivity::class.java)
            startActivity(intent)
        }

        //searchbar on click
        binding.searchBar.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }


    }//onViewCreated



    private fun observeSliderAds() {
        viewModel.getLiveSliderAds().observe(viewLifecycleOwner){list ->
            sliderAdsAdapter= SliderAdapter(
                ArrayList(list),
                object : OnClick{
                    override fun OnClick(index: Int) {
                        val intent = Intent(requireContext(), StoreActivity::class.java)
                        startActivity(intent)
                    }//onClick
                }//object
            )
            binding.rvSlider.adapter = sliderAdsAdapter
            binding.rvSlider.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.rvSlider)
            binding.indicator.attachToRecyclerView(binding.rvSlider, snapHelper)
            sliderAdsAdapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        }
    }


    private fun observeCategories2() {
        viewModel.getLiveCategories2().observe(viewLifecycleOwner) { list ->
            categoriesAdapter= CategoriesAdadpter(
                ArrayList(list),
                object : OnClick{
                    override fun OnClick(index: Int) {
                        val intent = Intent(requireContext(), CustomisedCategoryActivity::class.java)
                        startActivity(intent)
                    }
                }//object
            )
            binding.rvCategories.apply {
                adapter=categoriesAdapter
                binding.rvCategories.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun observeSellers() {
        viewModel.getLiveSellers().observe(viewLifecycleOwner){list->
            sellersAdapter= SuggestedStoresAdapter(
                ArrayList(list),
                object : OnClick{
                    override fun OnClick(index: Int) {
                        val intent = Intent(requireContext(), StoreActivity::class.java)
                        startActivity(intent)
                    }
                }//ob1ject
            )
            binding.rvSuggestedStores.adapter = sellersAdapter
            binding.rvSuggestedStores.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }

    }





}

