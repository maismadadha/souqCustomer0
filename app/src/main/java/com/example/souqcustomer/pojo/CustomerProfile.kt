package com.example.souqcustomer.pojo

data class CustomerProfile(
    val user_id: Int,
    val user: User?,
    val first_name: String,
    val last_name: String,
    val updated_at: String,
    val created_at: String
)