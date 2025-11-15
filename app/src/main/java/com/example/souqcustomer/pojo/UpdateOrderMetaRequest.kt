package com.example.souqcustomer.pojo

data class UpdateOrderMetaRequest(
    val delivery_fee: Double,
    val payment_method: String
)