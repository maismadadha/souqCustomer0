package com.example.souqcustomer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.CurrentOrdersAdapter
import com.example.souqcustomer.databinding.FragmentCurrentOrdersBinding


class CurrentOrders : Fragment() {

private lateinit var binding: FragmentCurrentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentCurrentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter= CurrentOrdersAdapter()
        binding.rvCurrentOrders.adapter=adapter
        binding.rvCurrentOrders.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }
}




