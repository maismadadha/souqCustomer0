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
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.activities.ProductActivity


class homeFragment : Fragment() {

    //autoSlider
    private val autoScrollDelay = 3000L // كل 3 ثواني
    private lateinit var snapHelper: PagerSnapHelper
    private lateinit var scrollListener: RecyclerView.OnScrollListener
    private val autoScrollHandler = android.os.Handler(android.os.Looper.getMainLooper())
    private val autoScrollRunnable: Runnable = object : Runnable {

        override fun run() {
            val rv = binding.rvSlider
            val lm = rv.layoutManager as? LinearLayoutManager ?: return
            val adapter = rv.adapter ?: return
            val count = adapter.itemCount
            if (count == 0) return

            val snapView = snapHelper.findSnapView(lm) ?: return
            val current = lm.getPosition(snapView)

            if (current == count - 1) {
                rv.scrollToPosition(0)
                rv.post {
                    autoScrollHandler.removeCallbacks(autoScrollRunnable)
                    autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollDelay)
                }
            } else {
                rv.smoothScrollToPosition(current + 1)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollDelay) // شغّل مرة وحدة هون
    }

    override fun onPause() {
        super.onPause()
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        autoScrollHandler.removeCallbacks(autoScrollRunnable) // تنظيف إضافي مهم
    }




    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var categoriesAdapter: CategoriesAdadpter
    private lateinit var sliderAdsAdapter: SliderAdapter
    private lateinit var sellersAdapter: SuggestedStoresAdapter
    private lateinit var newProductsAdapter: NewProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //sliderAds
        viewModel.getSliderAds()
        observeSliderAds()
        binding.rvSlider.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        snapHelper = PagerSnapHelper()
        binding.rvSlider.onFlingListener = null   // مهم لو كان مركّب قبل
        snapHelper.attachToRecyclerView(binding.rvSlider)
        binding.indicator.attachToRecyclerView(binding.rvSlider, snapHelper)
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    autoScrollHandler.removeCallbacks(autoScrollRunnable)
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    autoScrollHandler.postDelayed(autoScrollRunnable, autoScrollDelay)
                }
            }
        }
        binding.rvSlider.removeOnScrollListener(scrollListener) // احتياط
        binding.rvSlider.addOnScrollListener(scrollListener)



        //Categories
        viewModel.getCategories2()
        observeCategories2()

        //sellers
        viewModel.getSellers()
        observeSellers()

        //new products
        viewModel.getAllProducts()
        observeNewProducts()




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
        viewModel.getLiveSliderAds().observe(viewLifecycleOwner) { list ->
            sliderAdsAdapter = SliderAdapter(
                ArrayList(list),
                object : OnClick {
                    override fun OnClick(sellerId: Int) {
                        val intent = Intent(requireContext(), StoreActivity::class.java)
                        intent.putExtra("sellerId", sellerId)
                        startActivity(intent)
                    }
                }
            )

            binding.rvSlider.adapter = sliderAdsAdapter
            (binding.rvSlider.layoutManager as? LinearLayoutManager)?.scrollToPosition(0)

            // 3) اربط الـ indicator الآن (بعد ما صار فيه adapter) وسجّل الـ observer
            binding.indicator.attachToRecyclerView(binding.rvSlider, snapHelper)
            sliderAdsAdapter.registerAdapterDataObserver(binding.indicator.adapterDataObserver)

        }
    }




    private fun observeCategories2() {
        viewModel.getLiveCategories2().observe(viewLifecycleOwner) { list ->
            categoriesAdapter = CategoriesAdadpter(
                ArrayList(list),
                object : OnClick {
                    override fun OnClick(categoryId: Int) {
                        val intent = Intent(requireContext(), CustomisedCategoryActivity::class.java)
                        intent.putExtra("categoryId", categoryId)
                        intent.putExtra("categoryName", list[categoryId-1].name)
                        startActivity(intent)
                    }
                }//object
            )
            binding.rvCategories.apply {
                adapter = categoriesAdapter
                binding.rvCategories.layoutManager =
                    GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            }
        }
    }

    private fun observeSellers() {
        viewModel.getLiveSellers().observe(viewLifecycleOwner) { list ->
            sellersAdapter = SuggestedStoresAdapter(
                ArrayList(list),
                object : OnClick {
                    override fun OnClick(index: Int) {
                        val intent = Intent(requireContext(), StoreActivity::class.java)
                        intent.putExtra("sellerId", list[index].user_id)
                        startActivity(intent)
                    }
                }//ob1ject
            )
            binding.rvSuggestedStores.adapter = sellersAdapter
            binding.rvSuggestedStores.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }

    }

    private fun observeNewProducts() {
        viewModel.getLiveAllProducts().observe(viewLifecycleOwner) { list ->
            newProductsAdapter = NewProductsAdapter(
                ArrayList(list),
                object : OnClick {
                    override fun OnClick(index: Int) {
                        val intent = Intent(requireContext(), ProductActivity::class.java)
                        intent.putExtra("productId", list[index].id)
                        startActivity(intent)
                    }
                }//object
            )
            binding.rvNewProducts.layoutDirection = View.LAYOUT_DIRECTION_RTL
            binding.rvNewProducts.adapter = newProductsAdapter
            binding.rvNewProducts.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        }
    }


}

