package com.example.souqcustomer.pojo

data class User(
    val id: Int,
    val email: String?,
    val phone: String,
    val role: String,
    val created_at: String,
    val updated_at: String,
    val customer_profile: CustomerProfile?,
    val seller_profile: Any?,     // أو موديل منفصل لو رح تحتاجه لاحقًا
    val delivery_profile: Any?
)