package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.souqcustomer.activities.MainActivity
import com.example.souqcustomer.databinding.ActivityLoginBinding
import com.example.souqcustomer.activities.otpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var phone_number = binding.phoneNumber.text.toString()

        binding.confirmButton.setOnClickListener {

            var intent = Intent(this, otpActivity::class.java)
            startActivity(intent)

        }

        binding.createNewAccount.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}