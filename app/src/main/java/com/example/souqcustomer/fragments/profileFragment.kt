package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.AccountInformationActivity
import com.example.souqcustomer.activities.AddressesActivity
import com.example.souqcustomer.databinding.FragmentProfileBinding
import com.example.souqcustomer.viewModel.CustomerViewModel
import com.example.souqcustomer.viewModel.SellerViewModel


class profileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: CustomerViewModel
    private var city: String = ""
    private var fname: String = ""
    private var sname: String = ""
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[CustomerViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("souq_prefs", AppCompatActivity.MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)

        val prefs2 =
            requireContext().getSharedPreferences("souq_prefs", AppCompatActivity.MODE_PRIVATE)
        city = prefs2.getString("CITY_NAME", "")!!
        if (city != ""){
            binding.customerCity.text = city
        }
        else{
            binding.customerCity.text = "الزرقاء"
            city="الزرقاء"
        }

        viewModel.getCustomerProfile(userId)
        observeCustomer()



        binding.accountInformation.setOnClickListener {
            val intent = Intent(requireContext(), AccountInformationActivity::class.java)
            intent.putExtra("fname", fname)
            intent.putExtra("sname", sname)
            intent.putExtra("city", city)
            startActivity(intent)
        }

        binding.addresses.setOnClickListener {
            val intent = Intent(requireContext(), AddressesActivity::class.java)
            startActivity(intent)
        }

    }

    private fun observeCustomer() {
        viewModel.observeCustomer().observe(viewLifecycleOwner) { customer ->
            binding.customerName.text = "${customer.first_name} ${customer.last_name}"
            binding.customerChar.text = customer.first_name.first().toString()
            fname = customer.first_name
            sname = customer.last_name


    }}
    override fun onResume() {
        super.onResume()
        val prefs = requireContext().getSharedPreferences("souq_prefs", AppCompatActivity.MODE_PRIVATE)
        city = prefs.getString("CITY_NAME", "الزرقاء")!!
        binding.customerCity.text = city

        viewModel.getCustomerProfile(userId)
    }





}