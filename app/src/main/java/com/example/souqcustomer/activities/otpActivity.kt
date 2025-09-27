package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.souqcustomer.databinding.ActivityOtpBinding

class otpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var otp1 = binding.otp1.text.toString()
        var otp2 = binding.otp2.text.toString()
        var otp3 = binding.otp3.text.toString()
        var otp4 = binding.otp4.text.toString()

        binding.sendOtpButton.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


    }
}