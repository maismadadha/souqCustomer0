package com.example.souqcustomer.pojo

data class AddToCartRequest(
    val customer_id: Int,
    val store_id: Int,
    val product_id: Int,
    val quantity: Int,
    val price: Double
)
