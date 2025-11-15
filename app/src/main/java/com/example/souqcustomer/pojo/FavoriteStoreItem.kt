package com.example.souqcustomer.pojo

data class FavoriteStoreItem(
    val user_id: Int,
    val store_id: Int,
    val store_email: String,
    val store_phone: String,
    val store_name: String,
    val store_description: String,
    val store_logo_url: String?,
    val store_cover_url: String?,
    val main_category: String
)

