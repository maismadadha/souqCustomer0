package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.CustomisedCategoryActivity
import com.example.souqcustomer.adapters.CategoriesAdadpter
import com.example.souqcustomer.databinding.FragmentCategoriesBinding
import com.example.souqcustomer.interface0.OnClick


class categoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val categoriesAdapter = CategoriesAdadpter(object : OnClick{
            override fun OnClick(index: Int) {
                val intent = Intent(requireContext(), CustomisedCategoryActivity::class.java)
                startActivity(intent)
            }
        })
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
    }
}