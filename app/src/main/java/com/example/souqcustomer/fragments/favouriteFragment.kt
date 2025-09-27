package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.StoreActivity
import com.example.souqcustomer.adapters.FavouriteStoresAdapter
import com.example.souqcustomer.databinding.FragmentFavouriteBinding
import com.example.souqcustomer.interface0.OnClick


class favouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favouriteStoresAdapter = FavouriteStoresAdapter(object : OnClick{
            override fun OnClick(index: Int) {
                val intent = Intent(requireContext(), StoreActivity::class.java)
                startActivity(intent)
            }
        })
        binding.rvFavouriteStores.adapter = favouriteStoresAdapter
        binding.rvFavouriteStores.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }

}