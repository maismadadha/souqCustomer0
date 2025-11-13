package com.example.souqcustomer.pojo

data class ProductOptionsItem(
    val affects_variant: Int,
    val created_at: String,
    val id: Int,
    val label: String,
    val name: String,
    val product_id: Int,
    val required: Int,
    val selection: String,
    val sort_order: Int,
    val updated_at: String,
    val values: List<Value>
)