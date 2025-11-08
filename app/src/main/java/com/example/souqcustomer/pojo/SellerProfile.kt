package com.example.souqcustomer.pojo

data class SellerProfile(
    val created_at: String,
    val main_category_id: Int,
    val name: String,
    val password: String,
    val store_cover_url: String,
    val store_description: String,
    val store_logo_url: String,
    val updated_at: String,
    val user_id: Int
)