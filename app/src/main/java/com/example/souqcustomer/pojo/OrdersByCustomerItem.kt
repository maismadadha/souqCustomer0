package com.example.souqcustomer.pojo

data class OrdersByCustomerItem(
    val address: AddressDto,
    val address_id: Int,
    val created_at: String,
    val customer_id: Int,
    val delivery: Any,
    val delivery_fee: String,
    val discount_total: String,
    val id: Int,
    val items: List<OrderItemDto>,
    val items_count: Int,
    val note: String,
    val payment_method: String,
    val status: String,
    val store: Store,
    val store_id: Int,
    val store_name: String,
    val subtotal: String,
    val total_price: String,
    val updated_at: String
)