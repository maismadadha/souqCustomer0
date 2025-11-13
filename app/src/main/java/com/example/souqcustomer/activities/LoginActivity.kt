package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.souqcustomer.R
import com.example.souqcustomer.activities.MainActivity
import com.example.souqcustomer.databinding.ActivityLoginBinding
import com.example.souqcustomer.activities.otpActivity
import com.example.souqcustomer.viewModel.SignUpViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        observeLoginLiveData()
        observeErrorLoginLiveData()


        binding.confirmButton.setOnClickListener {

            var phone_number = binding.phoneNumber.text.toString()
            if (phone_number.isEmpty()) {
                showCustomToast("يرجى إدخال رقم الهاتف")
                return@setOnClickListener
            }
            viewModel.logIN(phone = phone_number)



        }

        binding.createNewAccount.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun observeErrorLoginLiveData() {
        viewModel.observeErrorLoginLiveData().observe(this) { errorMsg ->
            showCustomToast("المستخدم غير موجود")
        }
    }

    private fun observeLoginLiveData() {
        viewModel.observeLoginLiveData().observe(this){response ->
            val userId = response?.user?.user_id ?: 0
            if (userId == 0) {
                // ما وصل userId من الـAPI
                // showCustomToast("صار خطأ: ما وصل userId")
                return@observe
            }
            showCustomToast("يرجى ادخال رمز التحقق لتسحيل الدخول")
            var intent = Intent(this, otpActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
            finish()
        }
    }

    private fun showCustomToast(message: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, null)

        val tvMessage = layout.findViewById<TextView>(R.id.tvMessage)
        tvMessage.text = message

        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 160)
        toast.show()
    }



}


