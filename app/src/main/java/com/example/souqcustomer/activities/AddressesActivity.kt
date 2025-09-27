package com.example.souqcustomer.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.MyAddressesAdapter
import com.example.souqcustomer.databinding.ActivityAddressesBinding
import com.example.souqcustomer.interface0.OnClick

class AddressesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addresses)
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyAddressesAdapter(object : OnClick {
            override fun OnClick(index: Int) {
                return

            }
        })
        binding.rvMyAddresses.adapter = adapter
        binding.rvMyAddresses.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        binding.back.setOnClickListener {
            finish()
        }

    }
}