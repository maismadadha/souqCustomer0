package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.ActivityMainBinding
import com.example.souqcustomer.activities.otpActivity
import com.example.souqcustomer.viewModel.SignUpViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SignUpViewModel
    private lateinit var city: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        observeSignUpLiveData()
        observeErrorLiveData()


        binding.confirmButton.setOnClickListener {

            val first_name = binding.firstName.text.toString()
            val second_name = binding.secondName.text.toString()
            val phone_number = binding.phoneNumber.text.toString()
            val email: String? = null
            city= binding.citeName.text.toString()


            if (first_name.isEmpty() || second_name.isEmpty() || phone_number.isEmpty()) {
                showCustomToast("يرجى تعبئة جميع الحقول")
                return@setOnClickListener
            }

            viewModel.signUp(
                phone = phone_number,
                firstName = first_name,
                lastName = second_name,
                email = email
            )


        }



        binding.doYouHaveAccount.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun observeErrorLiveData() {
        viewModel.observeErrorLiveData().observe(this) { errorMsg ->
            showCustomToast(errorMsg)
        }
    }

    private fun observeSignUpLiveData() {
        viewModel.observeSignUpLiveData().observe(this) { response ->
            val userId = response.user.id

            if (userId == 0) {
                showCustomToast("صار خطأ: ما وصل userId من السيرفر")
                return@observe
            }

            showCustomToast("يرجى ادخال رمز التحقق لإنشاء الحساب")
            val intent = Intent(this, otpActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("city_name", city)
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