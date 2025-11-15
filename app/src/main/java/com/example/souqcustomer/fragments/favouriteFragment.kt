package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.StoreActivity
import com.example.souqcustomer.adapters.FavouriteStoresAdapter
import com.example.souqcustomer.databinding.FragmentFavouriteBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.FavoriteStores
import com.example.souqcustomer.viewModel.SellerViewModel


class favouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding
    private lateinit var viewModel: SellerViewModel
    private lateinit var favouriteStoresAdapter: FavouriteStoresAdapter
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("souq_prefs", AppCompatActivity.MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)

        favouriteStoresAdapter = FavouriteStoresAdapter(
            FavoriteStores(),
            object : OnClick {
                override fun OnClick(sellerId: Int) {
                    val intent = Intent(requireContext(), StoreActivity::class.java)
                    intent.putExtra("sellerId", sellerId) // مباشرة، بدون ما نعمل sellers[sellerId]
                    startActivity(intent)
                }
            }
        )

        binding.rvFavouriteStores.apply {
            adapter = favouriteStoresAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.getFavoritesSellersByUserId(userId)
        observeFavoritesSellers()
    }

    private fun observeFavoritesSellers() {
        viewModel.getLiveFavoritesSellers().observe(viewLifecycleOwner) { stores ->
            favouriteStoresAdapter.submitList(stores ?: FavoriteStores())

            if (stores.isNullOrEmpty()) {
                binding.rvFavouriteStores.visibility = View.GONE
                binding.tvEmptyFav.visibility = View.VISIBLE   // لو عندك TextView لهالشي
            } else {
                binding.rvFavouriteStores.visibility = View.VISIBLE
                binding.tvEmptyFav.visibility = View.GONE
            }
        }
    }
}
