package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.souqcustomer.databinding.ActivityMainBinding
import com.example.souqcustomer.activities.otpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var first_name = binding.firstName.text.toString()
        var second_name = binding.secondName.text.toString()
        var phone_number = binding.phoneNumber.text.toString()
        var city = binding.city.text.toString()
        binding.confirmButton.setOnClickListener {

            var intent = Intent(this, otpActivity::class.java)
            startActivity(intent)

        }

        binding.doYouHaveAccount.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}