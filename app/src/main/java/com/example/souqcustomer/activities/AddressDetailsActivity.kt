package com.example.souqcustomer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.souqcustomer.databinding.ActivityAddressDetailsBinding

class AddressDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val addressName = intent.getStringExtra("address_name") ?: ""
        val cityName = intent.getStringExtra("city_name") ?: ""
        val street = intent.getStringExtra("street") ?: ""
        val buildingNumber = intent.getStringExtra("building_number") ?: ""

    }
}
