package com.example.souqcustomer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.ActivityCheckoutBinding
import com.example.souqcustomer.pojo.AddressDto
import com.example.souqcustomer.viewModel.OrderViewModel


class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var orderViewModel: OrderViewModel

    private var orderId: Int = 0
    private var totalPrice: Double = 0.0
    private var selectedAddressId: Int? = null
    var selectedPaymentMethod = "cash"
    private var addresses: List<AddressDto> = emptyList()
    private var deliveryFee = 3.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        orderId = intent.getIntExtra("orderId", 0)
        totalPrice = intent.getDoubleExtra("totalPrice", 0.0)

        //عرض الفاتورة
        binding.subTotalPrice.text = totalPrice.toString()
        binding.deliveryPrice.text = deliveryFee.toString()
        binding.totalPrice.text = (totalPrice + deliveryFee).toString()

        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        val userId = prefs.getInt("USER_ID", 0)

        orderViewModel.getUserAddresses(userId)
        observeAddresses()
        observeConfirm()

        binding.sendOrderButton.setOnClickListener {
            if (orderId == 0) {
                Toast.makeText(this, "خطأ في رقم الطلب", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedAddressId == null) {
                Toast.makeText(this, "الرجاء اختيار عنوان للتوصيل", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            orderViewModel.setOrderAddress(orderId, selectedAddressId!!) { okAddress ->
                if (!okAddress) {
                    Toast.makeText(this, "فشل تعيين العنوان", Toast.LENGTH_SHORT).show()
                    return@setOrderAddress
                }

                orderViewModel.updateOrderMeta(
                    orderId,
                    deliveryFee,
                    selectedPaymentMethod
                ) { okMeta ->
                    if (!okMeta) {
                        Toast.makeText(this, "فشل تحديث بيانات الطلب", Toast.LENGTH_SHORT).show()
                        return@updateOrderMeta
                    }
                    orderViewModel.confirmOrder(orderId)

                }
            }
        }


        orderViewModel.observeConfirmResult().observe(this) { success ->
            if (success == true) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else if (success == false) {
                Toast.makeText(this, "فشل تأكيد الطلب، حاولي مرة ثانية", Toast.LENGTH_SHORT).show()
            }
        }


        //animation
        binding.deliveryAnimation.playAnimation()

        //back
        binding.back.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }

        setupPaymentOptions()



    }

    private fun setupPaymentOptions() {
        // كاش
        binding.optionCash.setOnClickListener {
            binding.radioCash.setImageResource(R.drawable.ic_radio_checked)
            binding.radioAddCard.setImageResource(R.drawable.ic_radio_unchecked)
            selectedPaymentMethod = "cash"
        }
        // بطاقة
        binding.optionAddCard.setOnClickListener {
            binding.radioAddCard.setImageResource(R.drawable.ic_radio_checked)
            binding.radioCash.setImageResource(R.drawable.ic_radio_unchecked)
            selectedPaymentMethod = "card"
        }
    }

    private fun observeConfirm() {
        orderViewModel.observeConfirmResult().observe(this) { success ->
            if (success == true) {
                showCustomToast()
                finish()
            } else if (success == false) {
                Toast.makeText(this, "فشل تأكيد الطلب، حاولي مرة ثانية", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeAddresses() {
        orderViewModel.observeAddresses().observe(this) { list ->
            addresses = list ?: emptyList()
            if (addresses.isNotEmpty()) {
                // نختار أول عنوان تلقائيًا
                val addr = addresses[0]
                selectedAddressId = addr.id
                showAddressOnUi(addr)
            } else {
                // ما في عناوين
                binding.addressName.text = "لا يوجد عناوين"
                binding.addressDetails.text = "أضف عنوانًا جديدًا من حسابك"
            }

            // زر تغيير العنوان
            binding.changeAddress.setOnClickListener {
                if (addresses.isEmpty()) return@setOnClickListener
                val names = addresses.map { it.address_name }.toTypedArray()
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("اختر عنوان التوصيل")
                    .setItems(names) { _, which ->
                        val addr = addresses[which]
                        selectedAddressId = addr.id
                        showAddressOnUi(addr)
                    }
                    .show()
            }
        }
    }

    private fun showAddressOnUi(addr: AddressDto) {
        binding.addressName.text = addr.address_name
        val details = "${addr.city_name} - ${addr.street} - بناية ${addr.building_number}" +
                (addr.address_note?.let { "\nملاحظة: $it" } ?: "")
        binding.addressDetails.text = details
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
