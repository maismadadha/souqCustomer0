package com.example.souqcustomer.pojo

data class SellersItem(
    val user_id: Int,
    val name: String,
    val main_category: Categories2Item,
    val main_category_id: Int?,
    val password: String?,
    val store_cover_url: String?,
    val store_description: String?,
    val store_logo_url: String?,

    val created_at: String?,
    val updated_at: String?,

    val preparation_days: Int?,
    val preparation_hours: Int?,
    val delivery_price: Double?,

    var isFavorite: Boolean = false
)
