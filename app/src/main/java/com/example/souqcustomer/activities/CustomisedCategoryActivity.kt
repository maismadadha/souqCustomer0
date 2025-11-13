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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customised_category)
        binding = ActivityCustomisedCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SellerViewModel::class.java]
        val categoryId   = intent.getIntExtra("categoryId", 0)
        val categoryName = intent.getStringExtra("categoryName").orEmpty()
        binding.categoryName.text = categoryName


        viewModel.getSellersByMainCategory(categoryId)
        observeSellersByMainCategory()






        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun observeSellersByMainCategory() {
        viewModel.getLiveSellersByMainCategory().observe(this) { sellers ->
            val adapter = CustomisedCategoryAdapter(sellers,object : OnClick {
                override fun OnClick(id: Int) {
                    val intent = Intent(this@CustomisedCategoryActivity, StoreActivity::class.java)
                    intent.putExtra("sellerId", id)
                    startActivity(intent)
                }
            }) //object
            binding.rvCustomisedCategory.adapter = adapter
            binding.rvCustomisedCategory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
    }
}