package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.souqcustomer.databinding.RvCurrentOrdersItemBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.pojo.OrdersByCustomerItem
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.ZonedDateTime


class CurrentOrdersAdapter(
    var orders: List<OrdersByCustomerItem>,
    val listener: OnClick
) : RecyclerView.Adapter<CurrentOrdersAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvCurrentOrdersItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val inputFormatter = DateTimeFormatter.ofPattern(
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
        Locale.US
    ) // تنسيق التاريخ مع `Z`
    private val dateFormatter =
        DateTimeFormatter.ofPattern("d MMMM", Locale("ar"))  // عرض التاريخ بالعربية، مثل: 15 نوفمبر
    private val timeFormatter =
        DateTimeFormatter.ofPattern("h:mm a", Locale("ar"))   // عرض الوقت بالعربية، مثل: 9:21 م


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            RvCurrentOrdersItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = orders[position]
        holder.binding.orderDate.text = formatDate(item.created_at)
        holder.binding.orderTime.text = formatTime(item.created_at)
        holder.binding.orderStatus.text = statusToArabic(item.status)


        holder.binding.storeName.text = item.store_name
        holder.binding.orderPrice.text = item.subtotal
        holder.binding.deliveryPrice.text = item.delivery_fee
        holder.binding.orderNumber.text = item.id.toString()
        Glide.with(holder.itemView.context)
            .load(item.store.seller_profile?.store_logo_url)
            .into(holder.binding.storeImg)



        holder.itemView.setOnClickListener {
            val pos = holder.adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                listener.OnClick(pos)
            }
        }

    }

    override fun getItemCount(): Int = orders.size
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
            "ON_CART" -> "في السلة"
            "CONFIRMED" -> "تم التأكيد"
            "PREPARING" -> "جاري التحضير"
            "READY_FOR_PICKUP" -> "الطلب جاهز"
            "OUT_FOR_DELIVERY" -> "في الطريق"
            "DELIVERED" -> "تم الاستلام"
            "CANCELLED" -> "ملغي"
            else -> status
        }
    }


}