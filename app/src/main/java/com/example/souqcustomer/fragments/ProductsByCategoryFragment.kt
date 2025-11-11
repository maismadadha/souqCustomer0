package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.activities.ProductActivity
import com.example.souqcustomer.adapters.ProductsByCategoryAdapter
import com.example.souqcustomer.databinding.FragmentProductsByCategoryBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.viewModel.SellerViewModel


class ProductsByCategoryFragment : Fragment() {

    companion object {
        private const val ARG_SELLER_ID = "sellerId"
        private const val ARG_CATEGORY_ID = "categoryId"

        fun newInstance(sellerId: Int, categoryId: Int) = ProductsByCategoryFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SELLER_ID, sellerId)
                putInt(ARG_CATEGORY_ID, categoryId)
            }
        }
    }

    private lateinit var viewModel: SellerViewModel
    private lateinit var binding: FragmentProductsByCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductsByCategoryBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sellerId = arguments?.getInt(ARG_SELLER_ID) ?: 0
        val categoryId = arguments?.getInt(ARG_CATEGORY_ID) ?: 0

        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]
        viewModel.getCategoryProducts(categoryId)
        observeCategoryPeoducts()


    }

    private fun observeCategoryPeoducts() {
        viewModel.getLiveCategoryProducts().observe(viewLifecycleOwner) { products ->
            val adapter = ProductsByCategoryAdapter(
                products,
                object : OnClick {
                    override fun OnClick(productId: Int) {
                        val intent = Intent(requireContext(), ProductActivity::class.java)
                        intent.putExtra("productId", productId)
                        startActivity(intent)
                    }
                })//object
            binding.rvProductsByCategory.adapter = adapter
            binding.rvProductsByCategory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        }
    }

}