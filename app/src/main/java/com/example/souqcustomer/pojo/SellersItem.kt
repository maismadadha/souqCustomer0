package com.example.souqcustomer.pojo

data class SellersItem(
    val user_id: Int,
    val name: String,
    val main_category: Categories2Item,
    val main_category_id: Int,
    val password: String,
    val store_cover_url: String,
    val store_description: String,
    val store_logo_url: String,
    val updated_at: String,
    val user: User,
    val created_at: String,

)