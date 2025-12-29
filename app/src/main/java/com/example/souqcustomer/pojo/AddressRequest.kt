package com.example.souqcustomer.pojo

data class AddressRequest(
    val city_name: String,
    val street: String,
    val building_number: Int,
    val address_name: String,
    val latitude: Double,
    val longitude: Double
)
