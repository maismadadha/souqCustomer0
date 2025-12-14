package com.example.souqcustomer.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.souqcustomer.R
import com.example.souqcustomer.adapters.OrderDetailsItemsAdapter
import com.example.souqcustomer.databinding.ActivityOrdersDetalisBinding
import com.example.souqcustomer.viewModel.OrderViewModel
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class OrdersDetalisActivity : AppCompatActivity() {

    private val inputFormatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
        Locale.US
    ) // تنسيق التاريخ مع `Z`
    private val dateFormatter =
        DateTimeFormatter.ofPattern("d MMMM", Locale("ar"))  // عرض التاريخ بالعربية، مثل: 15 نوفمبر
    private val timeFormatter =
        DateTimeFormatter.ofPattern("h:mm a", Locale("ar"))   // عرض الوقت بالعربية، مثل: 9:21 م

    private lateinit var binding: ActivityOrdersDetalisBinding

    private var userId: Int = 0
    private var orderId: Int = 0

    private lateinit var viewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrdersDetalisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("souq_prefs", MODE_PRIVATE)
        userId = prefs.getInt("USER_ID", 0)
        orderId = intent?.getIntExtra("orderId", 0) ?: 0
        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        viewModel.getOrderDetails(orderId)
        viewModel.observeOrderDetails().observe(this) { order ->
            if (order != null) {
                binding.storeName.text = order.store.seller_profile?.name
                binding.orderId.text = order.id.toString()
                binding.orderDate.text = formatDate(order.created_at)
                binding.orderTime.text = formatTime(order.created_at)
                binding.orderStatus.text = statusToArabic(order.status)
                binding.totalPrice.text = order.total_price
                binding.deliveryPrice.text = order.delivery_fee
                Glide.with(this)
                    .load(order.store.seller_profile?.store_logo_url)
                    .into(binding.storeLogo)
                val adapter = OrderDetailsItemsAdapter(order.items)
                binding.rvOrderItems.adapter = adapter
                binding.rvOrderItems.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
        }
        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun formatDate(dateString: String): String {
        return try {
            val zonedDateTime = ZonedDateTime.parse(dateString)  // استخدم parse مباشرة
                .withZoneSameInstant(ZoneId.of("Asia/Amman")) // تحويل إلى توقيت الأردن
            dateFormatter.format(zonedDateTime) // تنسيق التاريخ
        } catch (e: Exception) {
            "خطأ في التاريخ"
        }
    }

    private fun formatTime(dateString: String): String {
        return try {
            val zonedDateTime = ZonedDateTime.parse(dateString)  // استخدم parse مباشرة
                .withZoneSameInstant(ZoneId.of("Asia/Amman")) // تحويل إلى توقيت الأردن
            timeFormatter.format(zonedDateTime) // تنسيق الوقت
        } catch (e: Exception) {
            "خطأ في الوقت"
        }
    }

    private fun statusToArabic(status: String): String {
        return when (status) {
            "ON_CART" -> "الطلب موجود حالياً في السلة وبإمكانك تأكيده عند الانتهاء."
            "CONFIRMED" -> "تم استلام طلبك بنجاح وسيتم البدء بتجهيزه قريباً."
            "PREPARING" -> "جارٍ تجهيز طلبك الآن من قبل المتجر."
            "OUT_FOR_DELIVERY" -> "طلبك خرج للتوصيل وهو في الطريق إليك."
            "DELIVERED" -> "تم تسليم طلبك بنجاح. نتمنى لك تجربة ممتعة!"
            "CANCELLED" -> "تم إلغاء الطلب. إذا كان لديك أي استفسار لا تتردد بالتواصل معنا."
            else -> status
        }
    }
}