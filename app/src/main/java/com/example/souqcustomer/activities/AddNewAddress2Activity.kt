package com.example.souqcustomer.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.ActivityAddNewAddress2Binding
import com.example.souqcustomer.pojo.AddressRequest
import com.example.souqcustomer.viewModel.MapViewModel

class AddNewAddress2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewAddress2Binding
    private lateinit var viewModel: MapViewModel
    private var lat: Double? = null
    private var lng: Double? = null
    private var userId: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNewAddress2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)

        viewModel = ViewModelProvider(this)[MapViewModel::class.java]



        lat=intent.getStringExtra("lat")?.toDouble()
        lng=intent.getStringExtra("lng")?.toDouble()

        if (lat == null || lng == null) {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        observeViewModel()

        binding.saveLocationButton.setOnClickListener {
            saveAddress()
        }

        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun saveAddress() {
        val city = binding.cityName.text.toString().trim()
        val street = binding.streetName.text.toString().trim()
        val building = binding.buildingName.text.toString().trim()
        val addressName = binding.locationName.text.toString().trim()

        if (city.isEmpty() || street.isEmpty() ||
            building.isEmpty() || addressName.isEmpty()
        ) {
            Toast.makeText(this, "يرجى تعبئة جميع الحقول", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AddressRequest(
            city_name = city,
            street = street,
            building_number = building.toInt(),
            address_name = addressName,
            latitude = lat!!,
            longitude = lng!!
        )

        viewModel.sendAddressToApi(userId, request)
    }


    private fun observeViewModel() {
        viewModel.AddressLiveData().observe(this) { response ->
            if (response != null) {
                Toast.makeText(this, "تم حفظ العنوان بنجاح", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
        }
    }

}