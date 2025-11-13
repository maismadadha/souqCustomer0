package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.StoreActivity
import com.example.souqcustomer.adapters.FavouriteStoresAdapter
import com.example.souqcustomer.databinding.FragmentFavouriteBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.viewModel.SellerViewModel


class favouriteFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: SellerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoritesSellersByUserId(1)
        observeFavoritesSellers()




    }

    private fun observeFavoritesSellers() {
        viewModel.getLiveFavoritesSellers().observe(viewLifecycleOwner){sellers->
            val favouriteStoresAdapter=FavouriteStoresAdapter(sellers,
                object :OnClick{
                    override fun OnClick(index: Int) {
                        val intent = Intent(requireContext(), StoreActivity::class.java)
                        intent.putExtra("sellerId", sellers[index].user_id)
                        startActivity(intent)
                    }
                })
            binding.rvFavouriteStores.adapter = favouriteStoresAdapter
            binding.rvFavouriteStores.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        }
    }

}