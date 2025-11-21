package com.example.souqcustomer.pojo

data class Store(
    val id: Int,
    val email: String,
    val otp: Any,
    val phone: String,
    val role: String,
    val created_at: String,
    val updated_at: String,
    val seller_profile: SellersItem?,
)