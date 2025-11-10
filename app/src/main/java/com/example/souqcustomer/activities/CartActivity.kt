package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.CartItemsAdapter
import com.example.souqcustomer.databinding.ActivityCartBinding
import com.example.souqcustomer.interface0.OnClick

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CartItemsAdapter(object : OnClick{

            override fun OnClick(index: Int) {
                val intent = Intent(this@CartActivity, ProductActivity::class.java)
                startActivity(intent)
            }
        })
        binding.rvCart.adapter = adapter
        binding.rvCart.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.toPayButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }
    }
}