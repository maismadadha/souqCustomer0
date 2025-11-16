package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.CustomisedCategoryAdapter
import com.example.souqcustomer.databinding.ActivityCustomisedCategoryBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.viewModel.SellerViewModel

class CustomisedCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomisedCategoryBinding
    private lateinit var viewModel: SellerViewModel


    private var userId: Int = 0
    private lateinit var adapter: CustomisedCategoryAdapter
    private var favoriteStoreIds: Set<Int> = emptySet()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCustomisedCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)
        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]

        val categoryId   = intent.getIntExtra("categoryId", 0)
        val categoryName = intent.getStringExtra("categoryName").orEmpty()
        binding.categoryName.text = categoryName


        viewModel.getSellersByMainCategory(categoryId)
        observeSellersByMainCategory()

        viewModel.getFavoritesSellersByUserId(userId)
        observeFavorites()



        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun observeFavorites() {
        viewModel.getLiveFavoritesSellers().observe(this) { favorites ->
            if (favorites == null) return@observe

            // FavoriteStores = List<FavoriteItem> وفيها store_id
            favoriteStoreIds = favorites.map { it.store_id }.toSet()

            // لو الآدابتر صار جاهز، حدّث حالة isFavorite لكل seller
            if (::adapter.isInitialized) {
                adapter.Sellers.forEach { seller ->
                    seller.isFavorite = favoriteStoreIds.contains(seller.user_id)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }


    private fun observeSellersByMainCategory() {
        viewModel.getLiveSellersByMainCategory().observe(this) { sellers ->
            if (sellers == null) return@observe

            // نحولها ArrayList عشان نعدل عليها
            val sellersList = ArrayList(sellers)

            // نعلم مين منهم مفضل بناءً على favoriteStoreIds
            sellersList.forEach { seller ->
                seller.isFavorite = favoriteStoreIds.contains(seller.user_id)
            }

            adapter = CustomisedCategoryAdapter(
                sellersList,
                object : OnClick {
                    override fun OnClick(id: Int) {
                        val intent = Intent(this@CustomisedCategoryActivity, StoreActivity::class.java)
                        intent.putExtra("sellerId", id)
                        startActivity(intent)
                    }
                },
                onFavoriteClick = { seller, position ->
                    // لما يكبس القلب
                    if (seller.isFavorite) {
                        // صار مفضل → POST
                        viewModel.addFavoriteSeller(userId, seller.user_id)
                    } else {
                        // مبطل مفضل → DELETE
                        viewModel.removeFavoriteSeller(userId, seller.user_id)
                    }
                }
            )

            binding.rvCustomisedCategory.adapter = adapter
            binding.rvCustomisedCategory.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
    }

}