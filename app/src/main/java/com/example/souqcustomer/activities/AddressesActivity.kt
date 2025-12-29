package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.adapters.MyAddressesAdapter
import com.example.souqcustomer.databinding.ActivityAddressesBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.AddressDto
import com.example.souqcustomer.viewModel.OrderViewModel

class AddressesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressesBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var adapter: MyAddressesAdapter
    private var addresses: List<AddressDto> = emptyList()
    private var userId: Int = 0

    private val addAddressLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                orderViewModel.getUserAddresses(userId)
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddressesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        // Adapter مهيأ مرة وحدة فقط
        adapter = MyAddressesAdapter(addresses, object : OnClick {
            override fun OnClick(index: Int) {
               //
            }
        })

        binding.rvMyAddresses.adapter = adapter
        binding.rvMyAddresses.layoutManager = LinearLayoutManager(this)

        orderViewModel.getUserAddresses(userId)
        observeAddresses()

        binding.back.setOnClickListener { finish() }

        binding.addAddressBtn.setOnClickListener {
            val intent = Intent(this, AddNewAddressActivity::class.java)
            addAddressLauncher.launch(intent)
        }
    }

    private fun observeAddresses() {
        orderViewModel.observeAddresses().observe(this) { list ->
            addresses = list ?: emptyList()
            adapter.updateList(addresses)
        }
    }
}
