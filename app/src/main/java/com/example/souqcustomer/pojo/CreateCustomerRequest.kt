package com.example.souqcustomer.pojo

data class CreateCustomerRequest(
    val phone: String,
    val email: String?,          // ممكن يكون null
    val first_name: String,
    val last_name: String,
    val role: String = "customer" // ثابت
)