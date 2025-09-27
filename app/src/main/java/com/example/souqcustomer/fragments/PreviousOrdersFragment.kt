package com.example.souqcustomer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.previousOrdersAdapter
import com.example.souqcustomer.databinding.FragmentPreviousOrdersBinding


class PreviousOrdersFragment : Fragment() {

    private lateinit var binding: FragmentPreviousOrdersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
           // Inflate the layout for this fragment
        binding = FragmentPreviousOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter= previousOrdersAdapter()
        binding.rvPreviousOrders.adapter=adapter
        binding.rvPreviousOrders.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }


}