package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.adapters.SearchResultsAdapter
import com.example.souqcustomer.databinding.ActivitySearchBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.viewModel.SearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: SearchResultsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back
        binding.back.setOnClickListener {
            finish()
        }

        // ViewModel
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        // Adapter
        adapter = SearchResultsAdapter(object : OnClick {
            override fun OnClick(storeId: Int) {

                val intent = Intent(this@SearchActivity, StoreActivity::class.java)
                intent.putExtra("sellerId", storeId)
                startActivity(intent)
            }
        })

        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
        binding.rvSearchResults.adapter = adapter

        // Search listener
        binding.editText.addTextChangedListener {
            val query = it.toString().trim()

            if (query.length >= 2) {
                viewModel.search(query)
            } else {
                adapter.submitList(emptyList())
                binding.tvEmptySearch.visibility = View.GONE
                binding.rvSearchResults.visibility = View.GONE
            }
        }

        // Observe results
        viewModel.results.observe(this) { sellers ->

            // إذا حقل البحث فاضي → لا نعرض شي
            if (binding.editText.text.isNullOrEmpty()) {
                binding.tvEmptySearch.visibility = View.GONE
                binding.rvSearchResults.visibility = View.GONE
                return@observe
            }

            if (sellers.isEmpty()) {
                binding.tvEmptySearch.visibility = View.VISIBLE
                binding.rvSearchResults.visibility = View.GONE
            } else {
                binding.tvEmptySearch.visibility = View.GONE
                binding.rvSearchResults.visibility = View.VISIBLE
                adapter.submitList(sellers)
            }
        }
    }
}
