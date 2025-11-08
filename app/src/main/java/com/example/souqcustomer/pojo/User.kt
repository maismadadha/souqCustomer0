package com.example.souqcustomer.pojo

import com.google.gson.JsonElement

data class User(
    val created_at: String,
    val customer_profile: JsonElement?,
    val delivery_profile: JsonElement?,
    val email: String,
    val id: Int,
    val otp: String?,
    val phone: String,
    val role: String,
    val seller_profile: SellerProfile?,
    val updated_at: String
)