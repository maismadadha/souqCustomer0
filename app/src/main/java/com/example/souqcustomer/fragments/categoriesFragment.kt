package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.CustomisedCategoryActivity
import com.example.souqcustomer.adapters.AllCategoriesAdapter
import com.example.souqcustomer.adapters.CategoriesAdadpter
import com.example.souqcustomer.databinding.FragmentCategoriesBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.viewModel.UserViewModel


class categoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var categoriesAdapter : AllCategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategories2()
        observeCategories2()

    }

    private fun observeCategories2() {
        viewModel.getLiveCategories2().observe(viewLifecycleOwner) { list ->
            categoriesAdapter= AllCategoriesAdapter(
                ArrayList(list),
                object : OnClick{
                    override fun OnClick(index: Int) {
                        //
                    }

                    override fun OnClick1(index: Int) {
                        val intent = Intent(requireContext(), CustomisedCategoryActivity::class.java)
                        startActivity(intent)
                    }
                }
            )
            binding.rvCategories.apply {
                adapter = categoriesAdapter
                binding.rvCategories.layoutManager =
                 GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            }
        }
    }
}