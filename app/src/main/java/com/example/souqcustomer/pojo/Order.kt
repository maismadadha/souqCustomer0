package com.example.souqcustomer.pojo

data class Order(
    val address: AddressDto,
    val address_id: Int,
    val created_at: String,
    val customer_id: Int,
    val delivery_fee: String,
    val discount_total: String,
    val id: Int,
    val items_count: Int,
    val note: Any,
    val status: String,
    val store_id: Int,
    val subtotal: String,
    val total_price: String,
    val updated_at: String
)