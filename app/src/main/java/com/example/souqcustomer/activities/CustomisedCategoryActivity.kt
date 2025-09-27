package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.CustomisedCategoryAdapter
import com.example.souqcustomer.databinding.ActivityCustomisedCategoryBinding
import com.example.souqcustomer.interface0.OnClick

class CustomisedCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomisedCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customised_category)
        binding = ActivityCustomisedCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        val adapter = CustomisedCategoryAdapter(object : OnClick {
            override fun OnClick(index: Int) {
                val intent = Intent(this@CustomisedCategoryActivity, StoreActivity::class.java)
                startActivity(intent)
            }
        })
        binding.rvCustomisedCategory.adapter = adapter
       binding.rvCustomisedCategory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}