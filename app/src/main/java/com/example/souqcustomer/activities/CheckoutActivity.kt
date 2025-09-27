package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deliveryAnimation.playAnimation()

        binding.back.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }

        var selectedPaymentMethod = "cash"

        binding.optionCash.setOnClickListener {
            binding.radioCash.setImageResource(R.drawable.ic_radio_checked)
            binding.radioAddCard.setImageResource(R.drawable.ic_radio_unchecked)
            selectedPaymentMethod = "cash"
        }

        binding.optionAddCard.setOnClickListener {
            binding.radioAddCard.setImageResource(R.drawable.ic_radio_checked)
            binding.radioCash.setImageResource(R.drawable.ic_radio_unchecked)
            selectedPaymentMethod = "card"
        }

        binding.sendOrderButton.setOnClickListener {
            showCustomToast()
            finish()
        }
    }
    private fun showCustomToast() {
        val layoutInflater = layoutInflater
        val layout = layoutInflater.inflate(
            R.layout.custom_toast,
            findViewById(R.id.custom_toast_container) // root للـ LinearLayout
        )


        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }



}