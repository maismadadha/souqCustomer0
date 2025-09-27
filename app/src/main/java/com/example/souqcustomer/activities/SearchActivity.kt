package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.adapters.SearchResultsAdapter
import com.example.souqcustomer.databinding.ActivitySearchBinding
import com.example.souqcustomer.interface0.OnClick

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        val adapter = SearchResultsAdapter(object : OnClick{
            override fun OnClick(index: Int) {
                val intent = Intent(this@SearchActivity, StoreActivity::class.java)
                startActivity(intent)
            }
        })
        binding.rvSearchResults.adapter = adapter
        binding.rvSearchResults.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)



    }
}