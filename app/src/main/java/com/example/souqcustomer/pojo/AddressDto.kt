package com.example.souqcustomer.pojo

data class AddressDto(
    val address_name: String,
    val address_note: String,
    val building_number: Int,
    val city_name: String,
    val created_at: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val street: String,
    val updated_at: String,
    val user_id: Int
)