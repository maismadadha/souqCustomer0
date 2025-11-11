package com.example.souqcustomer.pojo

data class SellerCategoriesItem(
    val created_at: String,
    val id: Int,
    val name: String,
    val products: List<Product>,
    val store_id: Int,
    val updated_at: String
)