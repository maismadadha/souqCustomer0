package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.AccountInformationActivity
import com.example.souqcustomer.activities.AddressesActivity
import com.example.souqcustomer.databinding.FragmentProfileBinding


class profileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.accountInformation.setOnClickListener {
            val intent = Intent(requireContext(), AccountInformationActivity::class.java)
            startActivity(intent)
        }

        binding.addresses.setOnClickListener {
            val intent = Intent(requireContext(), AddressesActivity::class.java)
            startActivity(intent)
        }

    }


}