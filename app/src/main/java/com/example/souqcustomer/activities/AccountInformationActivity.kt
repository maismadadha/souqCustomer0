package com.example.souqcustomer.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.souqcustomer.databinding.ActivityAccountInformationBinding
import com.example.souqcustomer.pojo.CustomerProfileRequest
import com.example.souqcustomer.viewModel.CustomerViewModel

class AccountInformationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountInformationBinding
    private lateinit var viewModel: CustomerViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccountInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // جلب الـ userId من SharedPreferences
        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)

        // جلب اسم المدينة من SharedPreferences
        val cityFromPrefs = prefs.getString("CITY_NAME", "الزرقاء") ?: "الزرقاء"

        // جلب الاسم من intent (مؤقتًا قبل observe)
        val fname = intent.getStringExtra("fname").toString()
        val sname = intent.getStringExtra("sname").toString()

        binding.firstName.setText(fname)
        binding.secondName.setText(sname)
        binding.city.setText(cityFromPrefs)

        viewModel = ViewModelProvider(this)[CustomerViewModel::class.java]

        // بدء الملاحظة على بيانات العميل
        observeCustomer()

        binding.changeInfo.setOnClickListener {
            // تمكين التحرير
            binding.firstName.isFocusableInTouchMode = true
            binding.firstName.isClickable = true
            binding.secondName.isFocusableInTouchMode = true
            binding.secondName.isClickable = true
            binding.city.isFocusableInTouchMode = true
            binding.city.isClickable = true

            binding.saveButton.visibility = View.VISIBLE
            binding.changeInfo.visibility = View.GONE
        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.saveButton.setOnClickListener {
            val fnameToSave = binding.firstName.text.toString()
            val snameToSave = binding.secondName.text.toString()
            val cityToSave = binding.city.text.toString()

            // إرسال البيانات للتحديث
            val body = CustomerProfileRequest(fnameToSave, snameToSave)
            viewModel.updateCustomerProfile(userId, body)

            // حفظ المدينة محليًا
            prefs.edit().putString("CITY_NAME", cityToSave).apply()

            // تعطيل الحقول وإخفاء زر الحفظ
            binding.firstName.isFocusableInTouchMode = false
            binding.firstName.isClickable = false
            binding.secondName.isFocusableInTouchMode = false
            binding.secondName.isClickable = false
            binding.city.isFocusableInTouchMode = false
            binding.city.isClickable = false

            binding.saveButton.visibility = View.GONE
            binding.changeInfo.visibility = View.VISIBLE
        }
    }

    private fun observeCustomer() {
        viewModel.observeCustomer().observe(this) { customer ->
            // تحديث الـ UI بعد نجاح التحديث
            binding.firstName.setText(customer.first_name)
            binding.secondName.setText(customer.last_name)
        }
    }
}
